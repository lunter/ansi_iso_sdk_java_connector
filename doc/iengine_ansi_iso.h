#ifndef _INC_IENGINE_ANSI_ISO
#define _INC_IENGINE_ANSI_ISO

#ifdef __cplusplus 
extern "C" {
#endif 

#define IENGINE_API __declspec(dllimport)

typedef unsigned char BYTE;

typedef struct iengine_version 
{
    unsigned int Major;
    unsigned int Minor;
} IENGINE_VERSION, *IENGINE_VERSION_PTR;

/*
* Structure representing a particular minutia (distinctive fingerprint feature found in fingerprint skeleton, such as a bifurcation or an ending). 
*/
typedef struct iengine_minutiae
{
	/*
	* Minutia angle encoded in one byte. Valid range: 0-255. 
	*/
	BYTE angle;

	/*
	* Minutiae x coordinate as stored in the template.
	*/
	unsigned short x;

	/*
	* Minutiae y coordinate as stored in the template.
	*/
	unsigned short y;

	/*
	* Minutiae type (bifurcation/ending)
	*/
	unsigned char type;
} IENGINE_MINUTIAE, *IENGINE_MINUTIAE_PTR;

#define IENGINE_MIN_IMAGE_WIDTH 90
#define IENGINE_MAX_IMAGE_WIDTH 1800
#define IENGINE_MIN_IMAGE_HEIGHT 90
#define IENGINE_MAX_IMAGE_HEIGHT 1800

/*
Maximal size of generated ANSI/INCITS 378 template (with only one finger view)
*/
#define IENGINE_MAX_ANSI_TEMPLATE_SIZE 1568
/*
Maximal size of generated ISO/IEC 19794-2 template (with only one finger view)
*/
#define IENGINE_MAX_ISO_TEMPLATE_SIZE 1566


/*
* Enumeration defining codes for different template formats
*/
typedef enum
{
	ANSI_TEMPLATE = 0,
	ISO_TEMPLATE = 1,
	ILO_SID_TEMPLATE = 2
} IENGINE_TEMPLATE_FORMAT;


/*
* Enumeration defining codes for different finger positions
*/
typedef enum
{
	UNKNOWN_FINGER = 0,
	RIGHT_THUMB = 1,
	RIGHT_INDEX = 2,
	RIGHT_MIDDLE = 3,
	RIGHT_RING = 4,
	RIGHT_LITTLE = 5,
	LEFT_THUMB = 6,
	LEFT_INDEX = 7,
	LEFT_MIDDLE = 8,
	LEFT_RING = 9,
	LEFT_LITTLE = 10
} IENGINE_FINGER_POSITION;

/*
Summary: Defines sort order of minutiae points
*/
typedef enum
{
/*
No ordering required.
*/
	SORT_NONE = 0,
/*
Cartesian x-coordinate is used for ordering, ascending order.
*/
	SORT_X_ASC = 1,
/*
Cartesian x-coordinate is used for ordering, descending order.
*/
	SORT_X_DESC = 2,
/*
Cartesian y-coordinate is used for ordering, ascending order.
*/
	SORT_Y_ASC = 3,
/*
Cartesian y-coordinate is used for ordering, descending order.
*/
	SORT_Y_DESC = 4
} IENGINE_SORT_ORDER;

/*
* Defines impression type of fingerprint image 
*/
typedef enum
{
	TYPE_LIVE_SCAN_PLAIN = 0,
	TYPE_LIVE_SCAN_ROLLED = 1,
	TYPE_NONLIVE_SCAN_PLAIN = 2,
	TYPE_NONLIVE_SCAN_ROLLED = 3,
	TYPE_SWIPE = 4,
	TYPE_LIVE_SCAN_CONTACTLESS=9
} IENGINE_IMPRESSION_TYPE;


/* 
* Enumeration defining codes for parameters contained in ISO/IEC 19794-2 and ANSI/INCITS 378 compliant templates.
*/
typedef enum
{
	/* Specifies the 'owner' of the encoding application. This value is read-only (cannot be used with IEngine_SetTemplateParameter function). */
	PARAM_PRODUCT_OWNER = 0,

	/* Specifies the version of the encoding application. This value is read-only (cannot be used with IEngine_SetTemplateParameter function).*/
	PARAM_PRODUCT_VERSION = 1,

	/* Specifies the total length of the template in bytes. This value is read-only (cannot be used with IEngine_SetTemplateParameter function).*/
	PARAM_TEMPLATE_SIZE = 2,

	/*
	* Shall be a 4-bit value between 0 and 15, the most significant bit, if set to a 1,
	* indicates that the equipment was cerified to comply with Appendix F
	* (IAFIS Image Quality Specification, January 29, 1999) of FJIS-RS-0010,
	* the Federal Bureau of Investigations's Electronic Fingerprint Transmission Specification.
	* The other three bits are reserved for future compliance indicators.
	* The default value for this parameter is 0.
	*/
	PARAM_CAPTURE_EQUIPMENT_COMPLIANCE = 3,

	/*
	* Shall be recorded in twelve bits. 
	* A value of all zeros are acceptable and idicate that the capture equipment ID is unreported.
	* In other case, the value of the field is detemined by the vendor.
	* The default value for this parameter is 0.
	*/
	PARAM_CAPTURE_EQUIPMENT_ID = 4,

	/* 
	* Specifies total number of finger views contained within given template.
	* This value is read-only (cannot be used with IEngine_SetTemplateParameter function).
	*/
	PARAM_FINGER_VIEW_COUNT = 5,

	/* Specifies the finger position of the encoded fingerprint. 
	* The values of different finger positions are defined in <link IENGINE_FINGER_POSITION> enum.
	* The default value for this parameter is 0 (UNKNOWN_FINGER).
	*/
	PARAM_FINGER_POSITION = 10,

	/* Specifies the impression type of the encoded fingerprint. 
	* The values of different finger positions are defined in <link IENGINE_IMPRESSION_TYPE> enum.
	* The default value for this parameter is 0 (TYPE_LIVE_SCAN_PLAIN).
	*/
	PARAM_IMPRESSION_TYPE = 11,

	/*
	* Specifies the quality of the encoded fingerprint.
	* This quality number is an overall expression of the quality of the finger record, and represents quality of the original image.
	* A value of 0 represents the lowest possible quality and the value 100 represents the highest possible quality. The numeric
	* values in this field are set in accordance with the general guidelines contained in Section 2.1.42 of
	* ANSI/INCITS 358.
	* The default value for this parameter is 40 (fair quality).
	*/
	PARAM_FINGER_QUALITY = 12
} IENGINE_TEMPLATE_PARAMETER;




// Init, Terminate and other General Functions

IENGINE_API int IEngine_Init();
IENGINE_API int IEngine_Terminate();
IENGINE_API int IEngine_GetVersion(IENGINE_VERSION *version);
IENGINE_API char * IEngine_GetErrorMessage( int errcode );
IENGINE_API int IEngine_SetLicenseContent(const unsigned char *licenseContent, int length);


// Conversion Functions

IENGINE_API int ANSI_ConvertToISO(const BYTE *ansiTemplate,int *length,BYTE *isoTemplate);
IENGINE_API int ISO_ConvertToANSI(const BYTE *isoTemplate,int *length,BYTE *ansiTemplate);
IENGINE_API int ISO_ConvertToISOCardCC(const BYTE *isoTemplate,int maximumMinutiaeCount, IENGINE_SORT_ORDER minutiaeOrder, IENGINE_SORT_ORDER minutiaeSecondaryOrder, int *length,BYTE *isoCCTemplate);
IENGINE_API int ISO_CARD_CC_ConvertToISO(const BYTE *isoCCTemplate,int *length,BYTE *isoTemplate);
IENGINE_API int IEngine_GetImageQuality( int width, int height, const BYTE *rawImage, int *quality );
IENGINE_API int IEngine_LoadBMP(const char *filename,int *width, int *height,BYTE *rawImage, int *length);
IENGINE_API int IEngine_MakeBMP(int width, int height,const BYTE *rawImage, BYTE *bmpImageData, int *length);
IENGINE_API int IEngine_ConvertBMP(const BYTE *bmpImage,int *width, int *height,BYTE *rawImage, int *length);


// Template Extraction and Matching Functions

IENGINE_API int ANSI_CreateTemplate(int width, int height, const BYTE *rawImage, BYTE * ansiTemplate);
IENGINE_API int ANSI_CreateTemplateEx(int width, int height, const BYTE *rawImage, BYTE * ansiTemplate, const char *skeletonImageFile, const char *binarizedImageFile, const char *minutiaeImageFile);
IENGINE_API int ANSI_VerifyMatch(const BYTE *probeTemplate, const BYTE *galleryTemplate, int maxRotation, int *score); 
IENGINE_API int ANSI_VerifyMatchEx(const BYTE *probeTemplate, int probeView, const BYTE *galleryTemplate, int galleryView, int maxRotation, int *score); 

IENGINE_API int ISO_CreateTemplate(int width, int height, const BYTE *rawImage, BYTE * isoTemplate);
IENGINE_API int ISO_CreateTemplateEx(int width, int height, const BYTE *rawImage, BYTE * isoTemplate, const char *skeletonImageFile, const char *binarizedImageFile, const char *minutiaeImageFile);
IENGINE_API int ISO_CreateTemplateEx2(int width, int height, const BYTE *rawImage, BYTE * isoTemplate, BYTE *filteredImage, BYTE *binarizedImage, BYTE *skeletonImage, int *blockWidth, int *blockHeight, BYTE * bMask, BYTE *bOrientation, BYTE * bQuality)
IENGINE_API int ISO_VerifyMatch(const BYTE *probeTemplate, const BYTE *galleryTemplate, int maxRotation, int *score); 
IENGINE_API int ISO_VerifyMatchEx(const BYTE *probeTemplate, int probeView, const BYTE *galleryTemplate, int galleryView, int maxRotation, int *score); 
IENGINE_API int ISO_VerifyMatchEx2(const BYTE *probeTemplate, int probeView, const BYTE *galleryTemplate, int galleryView, int maxRotation, int *score,int*dx,int *dy,int *rotation,int *associationCount,BYTE *assocProbeMinutiae,BYTE *assocGalleryMinutiae,BYTE *assocQuality);


// Template Manipulation Functions

IENGINE_API int ANSI_GetTemplateParameter(const BYTE *ansiTemplate, IENGINE_TEMPLATE_PARAMETER parameter, int *value);
IENGINE_API int ANSI_SetTemplateParameter(BYTE *ansiTemplate, IENGINE_TEMPLATE_PARAMETER parameter, int value);
IENGINE_API int ANSI_GetFingerView(const BYTE *ansiTemplate,int fingerView,BYTE *outTemplate);
IENGINE_API int ANSI_DrawMinutiae(const BYTE *ansiTemplate,int width,int height, unsigned char *inputImage, unsigned char *outputBmpImage,int *outputImageLength);
IENGINE_API int ANSI_GetMinutiae(const BYTE *ansiTemplate, IENGINE_MINUTIAE minutiae[256], int *minutiaeCount);
IENGINE_API int ANSI_MergeTemplates(const BYTE *referenceTemplate,const BYTE *addedTemplate,int *length,BYTE *outTemplate);
IENGINE_API int ANSI_LoadTemplate(const char *filename, BYTE *ansiTemplate);
IENGINE_API int ANSI_RemoveMinutiae(const BYTE *inTemplate, int maximumMinutiaeCount, int *length, BYTE *outTemplate);
IENGINE_API int ANSI_SaveTemplate(const char *filename, const BYTE *ansiTemplate);

IENGINE_API int ISO_GetTemplateParameter(const BYTE *isoTemplate, IENGINE_TEMPLATE_PARAMETER parameter, int *value);
IENGINE_API int ISO_SetTemplateParameter(BYTE *isoTemplate, IENGINE_TEMPLATE_PARAMETER parameter, int value);
IENGINE_API int ISO_GetFingerView(const BYTE *isoTemplate,int fingerView,BYTE *outTemplate);
IENGINE_API int ISO_DrawMinutiae(const BYTE *isoTemplate,int width,int height, unsigned char *inputImage, unsigned char *outputBmpImage,int *outputImageLength);
IENGINE_API int ISO_GetMinutiae(const BYTE *isoTemplate, IENGINE_MINUTIAE minutiae[256], int *minutiaeCount);
IENGINE_API int ISO_MergeTemplates(const BYTE *referenceTemplate,const BYTE *addedTemplate,int *length,BYTE *outTemplate);
IENGINE_API int ISO_LoadTemplate(const char *filename, BYTE *isoTemplate);
IENGINE_API int ISO_RemoveMinutiae(const BYTE *inTemplate, int maximumMinutiaeCount, int *length, BYTE *outTemplate);
IENGINE_API int ISO_SaveTemplate(const char *filename, const BYTE *isoTemplate);

IENGINE_API int IEngine_ConvertTemplate(IENGINE_TEMPLATE_FORMAT inputTemplateType, const BYTE *inputTemplate, IENGINE_TEMPLATE_FORMAT outputTemplateType, int *length, BYTE *outputTemplate);



#define IENGINE_E_UNKNOWN_MSG		"Unknown error."
#define IENGINE_E_NOERROR             0
#define IENGINE_E_NOERROR_MSG	"No error."
#define IENGINE_E_BADPARAM            1101
#define IENGINE_E_BADPARAM_MSG	"Invalid parameter type provided."
#define IENGINE_E_BLANKIMAGE          1114
#define IENGINE_E_BLANKIMAGE_MSG "Image is blank or contains non-recognizable fingerprint."
#define IENGINE_E_BADIMAGE            1115
#define IENGINE_E_BADIMAGE_MSG  "Invalid image or unsupported image format."
#define IENGINE_E_INIT			1116
#define IENGINE_E_INIT_MSG		"Library was not initialized."
#define IENGINE_E_FILE                1117
#define IENGINE_E_FILE_MSG			"Error occured while opening/reading file."
#define IENGINE_E_MEMORY              1120
#define IENGINE_E_MEMORY_MSG		"Memory allocation failed."
#define IENGINE_E_NULLPARAM           1121
#define IENGINE_E_NULLPARAM_MSG		"NULL input parameter provided."
#define IENGINE_E_OTHER               1122
#define IENGINE_E_OTHER_MSG			"Other unspecified error."
#define IENGINE_E_BADLICENSE	      1129
#define IENGINE_E_BADLICENSE_MSG "Provided license is not valid, or no license was found."
#define IENGINE_E_BADFORMAT           1132
#define IENGINE_E_BADFORMAT_MSG "Unsupported format."
#define IENGINE_E_BADVALUE            1133
#define IENGINE_E_BADVALUE_MSG "Invalid value provided."
#define IENGINE_E_BADTEMPLATE         1135
#define IENGINE_E_BADTEMPLATE_MSG	"Invalide template or unsupported template format."
#define IENGINE_E_READONLY            1136
#define IENGINE_E_READONLY_MSG	"Value cannot be modified."
#define IENGINE_E_NOTDEFINED          1137
#define IENGINE_E_NOTDEFINED_MSG	"Value is not defined."
#define IENGINE_E_NULLTEMPLATE        1138
#define IENGINE_E_NULLTEMPLATE_MSG    "Template is NULL (contains no finger view)."

#ifdef __cplusplus 
}
#endif 

#endif
