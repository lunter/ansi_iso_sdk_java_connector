package com.innovatrics.iengine.ansiiso;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

/**
 * JNA bindings for the IEngine ANSI-ISO API. Requires the JNA library version 3.0.9.
 * @author Martin Vysny
 */
public class AnsiIso {

    private static void checkNotNull(String name, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Parameter " + name + " is null");
        }
    }

    /**
     * Enumeration defining codes for different finger positions
     */
    public static enum FingerPosition {

        UNKNOWN_FINGER,
        RIGHT_THUMB,
        RIGHT_INDEX,
        RIGHT_MIDDLE,
        RIGHT_RING,
        RIGHT_LITTLE,
        LEFT_THUMB,
        LEFT_INDEX,
        LEFT_MIDDLE,
        LEFT_RING,
        LEFT_LITTLE;
    }
    public static final int IENGINE_MIN_IMAGE_WIDTH = 90;
    public static final int IENGINE_MAX_IMAGE_WIDTH = 1800;
    public static final int IENGINE_MIN_IMAGE_HEIGHT = 90;
    public static final int IENGINE_MAX_IMAGE_HEIGHT = 1800;
    /*
    Maximal size of generated ANSI/INCITS 378 template (with only one finger view)
     */
    public static final int IENGINE_MAX_ANSI_TEMPLATE_SIZE = 1568;
    /*
    Maximal size of generated ISO/IEC 19794-2 template (with only one finger view)
     */
    public static final int IENGINE_MAX_ISO_TEMPLATE_SIZE = 1566;

    private static interface AnsiIsoNative extends Library {

        final AnsiIsoNative INSTANCE = (AnsiIsoNative) Native.loadLibrary("iengine_ansi_iso", AnsiIsoNative.class); // NOI18N
        public static final String IENGINE_E_UNKNOWN_MSG = "Unknown error.";
        public static final int IENGINE_E_NOERROR = 0;
        public static final String IENGINE_E_NOERROR_MSG = "No error.";
        public static final int IENGINE_E_BADPARAM = 1101;
        public static final String IENGINE_E_BADPARAM_MSG = "Invalid parameter type provided.";
        public static final int IENGINE_E_BLANKIMAGE = 1114;
        public static final String IENGINE_E_BLANKIMAGE_MSG = "Image is blank or contains non-recognizable fingerprint.";
        public static final int IENGINE_E_BADIMAGE = 1115;
        public static final String IENGINE_E_BADIMAGE_MSG = "Invalid image or unsupported image format.";
        public static final int IENGINE_E_INIT = 1116;
        public static final String IENGINE_E_INIT_MSG = "Library was not initialized.";
        public static final int IENGINE_E_FILE = 1117;
        public static final String IENGINE_E_FILE_MSG = "Error occured while opening/reading file.";
        public static final int IENGINE_E_MEMORY = 1120;
        public static final String IENGINE_E_MEMORY_MSG = "Memory allocation failed.";
        public static final int IENGINE_E_NULLPARAM = 1121;
        public static final String IENGINE_E_NULLPARAM_MSG = "NULL input parameter provided.";
        public static final int IENGINE_E_OTHER = 1122;
        public static final String IENGINE_E_OTHER_MSG = "Other unspecified error.";
        public static final int IENGINE_E_BADLICENSE = 1129;
        public static final String IENGINE_E_BADLICENSE_MSG = "Provided license is not valid, or no license was found.";
        public static final int IENGINE_E_BADFORMAT = 1132;
        public static final String IENGINE_E_BADFORMAT_MSG = "Unsupported format.";
        public static final int IENGINE_E_BADVALUE = 1133;
        public static final String IENGINE_E_BADVALUE_MSG = "Invalid value provided.";
        public static final int IENGINE_E_BADTEMPLATE = 1135;
        public static final String IENGINE_E_BADTEMPLATE_MSG = "Invalide template or unsupported template format.";
        public static final int IENGINE_E_READONLY = 1136;
        public static final String IENGINE_E_READONLY_MSG = "Value cannot be modified.";
        public static final int IENGINE_E_NOTDEFINED = 1137;
        public static final String IENGINE_E_NOTDEFINED_MSG = "Value is not defined.";
        public static final int IENGINE_E_NULLTEMPLATE = 1138;
        public static final String IENGINE_E_NULLTEMPLATE_MSG = "Template is NULL (contains no finger view).";

        // Init, Terminate and other General Functions
        int IEngine_Init();

        int IEngine_Terminate();

        int IEngine_GetVersion(IEngineVersion version);

        String IEngine_GetErrorMessage(int errcode);

        int IEngine_SetLicenseContent(byte[] licenseContent, int length);

        // Conversion Functions
        int ANSI_ConvertToISO(byte[] ansiTemplate, IntByReference length, byte[] isoTemplate);

        int ISO_ConvertToANSI(byte[] isoTemplate, IntByReference length, byte[] ansiTemplate);

        int ISO_ConvertToISOCardCC(byte[] isoTemplate, int maximumMinutiaeCount, int /*IENGINE_SORT_ORDER*/ minutiaeOrder, int /*IENGINE_SORT_ORDER*/ minutiaeSecondaryOrder, IntByReference length, byte[] isoCCTemplate);

        int ISO_CARD_CC_ConvertToISO(byte[] isoCCTemplate, IntByReference length, byte[] isoTemplate);

        int IEngine_GetImageQuality(int width, int height, final byte[] rawImage, IntByReference quality);

        int IEngine_LoadBMP(final String filename, IntByReference width, IntByReference height, byte[] rawImage, IntByReference length);

        int IEngine_ConvertBMP(final byte[] bmpImage, IntByReference width, IntByReference height, byte[] rawImage, IntByReference length);

        // Template Extraction and Matching Functions
        int ANSI_CreateTemplate(int width, int height, final byte[] rawImage, byte[] ansiTemplate);

        int ANSI_CreateTemplateEx(int width, int height, final byte[] rawImage, byte[] ansiTemplate, final String skeletonImageFile, final String binarizedImageFile, final String minutiaeImageFile);

        int ANSI_VerifyMatch(final byte[] probeTemplate, final byte[] galleryTemplate, int maxRotation, IntByReference score);

        int ANSI_VerifyMatchEx(final byte[] probeTemplate, int probeView, final byte[] galleryTemplate, int galleryView, int maxRotation, IntByReference score);

        int ISO_CreateTemplate(int width, int height, final byte[] rawImage, byte[] isoTemplate);

        int ISO_CreateTemplateEx(int width, int height, final byte[] rawImage, byte[] isoTemplate, final String skeletonImageFile, final String binarizedImageFile, final String minutiaeImageFile);

        int ISO_VerifyMatch(final byte[] probeTemplate, final byte[] galleryTemplate, int maxRotation, IntByReference score);

        int ISO_VerifyMatchEx(final byte[] probeTemplate, int probeView, final byte[] galleryTemplate, int galleryView, int maxRotation, IntByReference score);

        // Template Manipulation Functions
        int ANSI_GetTemplateParameter(final byte[] ansiTemplate, /*IENGINE_TEMPLATE_PARAMETER*/ int parameter, IntByReference value);

        int ANSI_SetTemplateParameter(byte[] ansiTemplate, /*IENGINE_TEMPLATE_PARAMETER*/ int parameter, int value);

        int ANSI_GetFingerView(final byte[] ansiTemplate, int fingerView, byte[] outTemplate);

        int ANSI_DrawMinutiae(final byte[] ansiTemplate, int width, int height, byte[] inputImage, byte[] outputBmpImage, IntByReference outputImageLength);

        int ANSI_GetMinutiae(final byte[] ansiTemplate, IEngineMinutiae[] minutiae, IntByReference minutiaeCount);

        int ANSI_MergeTemplates(final byte[] referenceTemplate, final byte[] addedTemplate, IntByReference length, byte[] outTemplate);

        int ANSI_LoadTemplate(final String filename, byte[] ansiTemplate);

        int ANSI_RemoveMinutiae(byte[] inTemplate, int maximumMinutiaeCount, IntByReference length, byte[] outTemplate);

        int ANSI_SaveTemplate(final String filename, final byte[] ansiTemplate);

        int ISO_GetTemplateParameter(final byte[] isoTemplate, /*IENGINE_TEMPLATE_PARAMETER*/ int parameter, IntByReference value);

        int ISO_SetTemplateParameter(byte[] isoTemplate, /*IENGINE_TEMPLATE_PARAMETER*/ int parameter, int value);

        int ISO_GetFingerView(final byte[] isoTemplate, int fingerView, byte[] outTemplate);

        int ISO_DrawMinutiae(final byte[] isoTemplate, int width, int height, byte[] inputImage, byte[] outputBmpImage, IntByReference outputImageLength);

        int ISO_GetMinutiae(final byte[] isoTemplate, IEngineMinutiae[] minutiae, IntByReference minutiaeCount);

        int ISO_MergeTemplates(final byte[] referenceTemplate, final byte[] addedTemplate, IntByReference length, byte[] outTemplate);

        int ISO_LoadTemplate(final String filename, byte[] isoTemplate);

        int ISO_RemoveMinutiae(byte[] inTemplate, int maximumMinutiaeCount, IntByReference length, byte[] outTemplate);

        int ISO_SaveTemplate(final String filename, final byte[] isoTemplate);

        int IEngine_ConvertTemplate(/*IENGINE_TEMPLATE_FORMAT*/int inputTemplateType, byte[] inputTemplate, /*IENGINE_TEMPLATE_FORMAT*/ int outputTemplateType, IntByReference length, byte[] outputTemplate);
    }

    private void check(int result) {
        String errMsg = null;
        switch (result) {
            case AnsiIsoNative.IENGINE_E_NOERROR:
                return;
            case AnsiIsoNative.IENGINE_E_BADPARAM:
                errMsg = AnsiIsoNative.IENGINE_E_BADPARAM_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_BLANKIMAGE:
                errMsg = AnsiIsoNative.IENGINE_E_BLANKIMAGE_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_BADIMAGE:
                errMsg = AnsiIsoNative.IENGINE_E_BADIMAGE_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_INIT:
                errMsg = AnsiIsoNative.IENGINE_E_INIT_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_FILE:
                errMsg = AnsiIsoNative.IENGINE_E_FILE_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_MEMORY:
                errMsg = AnsiIsoNative.IENGINE_E_MEMORY_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_NULLPARAM:
                errMsg = AnsiIsoNative.IENGINE_E_NULLPARAM_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_OTHER:
                errMsg = AnsiIsoNative.IENGINE_E_OTHER_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_BADLICENSE:
                errMsg = AnsiIsoNative.IENGINE_E_BADLICENSE_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_BADFORMAT:
                errMsg = AnsiIsoNative.IENGINE_E_BADFORMAT_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_BADVALUE:
                errMsg = AnsiIsoNative.IENGINE_E_BADVALUE_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_BADTEMPLATE:
                errMsg = AnsiIsoNative.IENGINE_E_BADTEMPLATE_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_READONLY:
                errMsg = AnsiIsoNative.IENGINE_E_READONLY_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_NOTDEFINED:
                errMsg = AnsiIsoNative.IENGINE_E_NOTDEFINED_MSG;
                break;
            case AnsiIsoNative.IENGINE_E_NULLTEMPLATE:
                errMsg = AnsiIsoNative.IENGINE_E_NULLTEMPLATE_MSG;
                break;
            default:
                errMsg = AnsiIsoNative.INSTANCE.IEngine_GetErrorMessage(result);
                break;
        }
        throw new AnsiIsoException(errMsg == null ? "Unknown error #" + result : errMsg, result);
    }

    // Init, Terminate and other General Functions
    /**
     * Initializes the library.
     * <p/>
     * This function initializes and checks the integrity of the library and verifies the validity of the license. It should be called prior to
    any other function from the library.
     */
    public void init() {
        check(AnsiIsoNative.INSTANCE.IEngine_Init());
    }

    /**
     * Terminates the use of the library<p/>
     * This function releases all resources allocated by the library. It should be called as the very last function of the library.
     */
    public void terminate() {
        check(AnsiIsoNative.INSTANCE.IEngine_Terminate());
    }

    /**
     * Returns the library version
     * @return the library version
     */
    public IEngineVersion getVersion() {
        final IEngineVersion result = new IEngineVersion();
        check(AnsiIsoNative.INSTANCE.IEngine_GetVersion(result));
        return result;
    }

    /**
     * Activates the library with a license key.<p/>
     * Use this function if you want to avoid storing license files on filesystem. This prevents a potential hacker to steal your license
    file information.
     * @param licenseContent  License information as provided by Innovatrics
     * @param length Total length of license data
     */
    public void setLicenseContent(byte[] licenseContent, int length) {
        check(AnsiIsoNative.INSTANCE.IEngine_SetLicenseContent(licenseContent, length));
    }

    // Conversion Functions
    /**
     * Converts ANSI/INCITS 378 compliant template to ISO/IEC 19794-2 compliant template.
     * <p/>
     * This function converts an input ANSI/INCITS 378 compliant template to an output ISO/IEC 19794-2 compliant template.
    Templates with multiple finger views are supported by this function.
     * @param ansiTemplate Reference ANSI/INCITS 378 template
     * @return the resulting ISO/IEC 19794-2 compliant template
     */
    public byte[] ansiConvertToISO(byte[] ansiTemplate) {
        checkNotNull("ansiTemplate", ansiTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ANSI_ConvertToISO(ansiTemplate, length, null));
        final byte[] isoTemplate = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ANSI_ConvertToISO(ansiTemplate, length, isoTemplate));
        return isoTemplate;
    }

    /**
     * Converts ISO/IEC 19794-2 compliant template to ANSI/INCITS 378 compliant template
     * <p/>
     * This function converts an input ISO/IEC 19794-2 compliant template to an output ANSI/INCITS 378 compliant template.
    Templates with multiple finger views are supported by this function.
     * @param isoTemplate  Reference ISO/IEC 19794-2 template
     * @return the resulting ANSI/INCITS 378 compliant template
     */
    public byte[] isoConvertToANSI(byte[] isoTemplate) {
        checkNotNull("isoTemplate", isoTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_ConvertToANSI(isoTemplate, length, null));
        final byte[] ansiTemplate = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_ConvertToANSI(isoTemplate, length, ansiTemplate));
        return ansiTemplate;
    }

    /**
     * Converts a regular ISO template to ISO Compact Card template format (ISO Compact Size Finger Minutiae Format)
     * <p/>
     * This function takes as input an ISO/IEC 19794-2 compliant fingerprint template and converts it into Finger Minutiae Compact
    Card Format template (ISO_CARD_CC template). An ISO_CARD_CC template is defined in ISO/IEC 19794-2 standard,
    under paragraph 8. In ISO_CARD_CC template, each minutiae point is encoded in 3 bytes (instead of 6 bytes used in
    regular ISO template). This function can also truncate minutiae points, if the number of minutiae points in the template is too
    big. You may also specify desired minutiae order in which minutiae points should be stored in the template.
     * @param isoTemplate Reference ISO/IEC 19794-2 template
     * @param maximumMinutiaeCount     The maximal number of minutiae that will be stored in the output template. See {@link #isoRemoveMinutiae} for details about truncation
    algorithm.
     * @param minutiaeOrder Defines the primary ordering criteria for minutiae
     * @param minutiaeSecondaryOrder Defines the secondary ordering criteria for minutiae (used when primary ordering criteria gives equality)
     * @return  the resulting ISO Compact Card template
     */
    public byte[] isoConvertToISOCardCC(byte[] isoTemplate, int maximumMinutiaeCount, SortOrder minutiaeOrder, SortOrder minutiaeSecondaryOrder) {
        checkNotNull("isoTemplate", isoTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_ConvertToISOCardCC(isoTemplate, maximumMinutiaeCount, minutiaeOrder.ordinal(), minutiaeSecondaryOrder.ordinal(), length, null));
        final byte[] isoCCTemplate = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_ConvertToISOCardCC(isoTemplate, maximumMinutiaeCount, minutiaeOrder.ordinal(), minutiaeSecondaryOrder.ordinal(), length, isoCCTemplate));
        return isoCCTemplate;
    }

    /**
     * Converts an ISO Compact Card template into regular ISO template.<p/>
     * This function takes as input a Finger Minutiae Compact Card Format template (ISO_CARD_CC template) and converts it
    into ISO/IEC 19794-2 compliant fingerprint template. An ISO_CARD_CC template is defined in ISO/IEC 19794-2 standard,
    under paragraph 8. In ISO_CARD_CC template, each minutiae point is encoded in 3 bytes (instead of 6 bytes used in
    regular ISO template).
     * @param isoCCTemplate ISO Compact Card template
     * @return the resulting ISO template
     */
    public byte[] isoCardCCConvertToISO(byte[] isoCCTemplate) {
        checkNotNull("isoCCTemplate", isoCCTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_CARD_CC_ConvertToISO(isoCCTemplate, length, null));
        final byte[] isoTemplate = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_CARD_CC_ConvertToISO(isoCCTemplate, length, isoTemplate));
        return isoTemplate;
    }

    /**
     * Returns quality of a fingerprint image
     * @param width The number of pixels indicating the width of the image
     * @param height The number of pixels indicating the height of the image
     * @param rawImage Pointer to the uncompressed raw image for template creation
     * @return Fingerprint image quality, the output range is from 0 (lowest quality) to 100 (highest quality)
     */
    public int getImageQuality(int width, int height, final byte[] rawImage) {
        checkNotNull("rawImage", rawImage);
        final IntByReference quality = new IntByReference();
        check(AnsiIsoNative.INSTANCE.IEngine_GetImageQuality(width, height, rawImage, quality));
        return quality.getValue();
    }

    /**
     * Loads bmp image from file and converts it to raw 8-bit format.<p/>
     * This function reads bmp image contained in a file and converts it to raw 8-bit format as described in paragraph Fingerprint
    Image Data ( see page 2)
     * @param filename Name of the file containing the input bmp image
     * @return input bmp image is converted to 8-bit raw image format
     */
    public RawImage loadBMP(final String filename) {
        checkNotNull("filename", filename);
        final IntByReference width = new IntByReference();
        final IntByReference height = new IntByReference();
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.IEngine_LoadBMP(filename, width, height, null, length));
        final byte[] rawImage = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.IEngine_LoadBMP(filename, width, height, rawImage, length));
        return new RawImage(width.getValue(), height.getValue(), rawImage);
    }

    /**
     * Reads bmp image from memory and converts it to raw 8-bit format.<p/>
     * This function reads bmp image encoded in a byte array and converts it to raw 8-bit format as described in paragraph
    Fingerprint Image Data ( see page 2)
     * @param bmpImage Pointer to the image in BMP format stored in the memory
     * @return input bmp image will be converted to 8-bit raw image format
     */
    public RawImage convertBMP(final byte[] bmpImage) {
        checkNotNull("bmpImage", bmpImage);
        final IntByReference width = new IntByReference();
        final IntByReference height = new IntByReference();
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.IEngine_ConvertBMP(bmpImage, width, height, null, length));
        final byte[] rawImage = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.IEngine_ConvertBMP(bmpImage, width, height, rawImage, length));
        return new RawImage(width.getValue(), height.getValue(), rawImage);
    }

    // Template Extraction and Matching Functions
    /**
     * Creates ANSI/INCITS 378 compliant template.<p/>
     * This function takes a raw image as input and generates the corresponding ANSI/INCITS 378 compliant fingerprint template.
    The memory for the template is allocated before the call (i.e., ANSI_CreateTemplate does not handle the memory allocation
    for the template parameter).
     * @param width The number of pixels indicating the width of the image
     * @param height The number of pixels indicating the height of the image
     * @param rawImage Pointer to the uncompressed raw image for template creation
     * @return the processed template. The maximal size of
    generated template is 1568 bytes.
     */
    public byte[] ansiCreateTemplate(int width, int height, final byte[] rawImage) {
        final byte[] result = new byte[IENGINE_MAX_ANSI_TEMPLATE_SIZE];
        check(AnsiIsoNative.INSTANCE.ANSI_CreateTemplate(width, height, rawImage, result));
        // @TODO mvy: alter the length of the result array according to the real size of the template
        return result;
    }

    /**
     * Creates ANSI/INCITS 378 compliant template, stores intermediate images.<p/>
     * This function takes a raw image as input and generates the corresponding ANSI/INCITS 378 compliant fingerprint template.
    It optionally stores intermediate images produced during the extraction phase. The memory for the template is allocated
    before the call (i.e., ANSI_CreateTemplate ( see page 12) does not handle the memory allocation for the template
    parameter).
     * @param width The number of pixels indicating the width of the image
     * @param height The number of pixels indicating the height of the image
     * @param rawImage the uncompressed raw image for template creation
     * @param skeletonImageFile Specifies the filename of bmp image where the fingerprint skeleton image will be saved. If this parameter is NULL, no skeleton image is saved.
     * @param binarizedImageFile Specifies the filename of bmp image where the fingerprint binary image will be saved. If this parameter is NULL, no binary image is saved.
     * @param minutiaeImageFile Specifies the filename of bmp image where the minutiae image will be saved (original fingerprint with all detected minutiae). If this parameter is NULL, no
    minutiae image is saved.
     * @return the processed template, the maximal size of
    generated templates is 1568 bytes.
     */
    public byte[] ansiCreateTemplateEx(int width, int height, final byte[] rawImage, final String skeletonImageFile, final String binarizedImageFile, final String minutiaeImageFile) {
        final byte[] result = new byte[IENGINE_MAX_ANSI_TEMPLATE_SIZE];
        check(AnsiIsoNative.INSTANCE.ANSI_CreateTemplateEx(width, height, rawImage, result, skeletonImageFile, binarizedImageFile, minutiaeImageFile));
        // @TODO mvy: alter the length of the result array according to the real size of the template
        return result;
    }

    /**
     * Compares two ANSI/INCITS 378 compliant templates.<p/>
     * This function compares two ANSI/INCITS 378 compliant templates and outputs a match score. The score returned is an
     * integer value ranging from 0 to 100000 which represents the similarity of original fingerprint images corresponding to
     * compared templates. See topic Matching Scores ( see page 3) for more details.
     * @param probeTemplate ANSI/INCITS 378 template
     * @param galleryTemplate ANSI/INCITS 378 template
     * @param maxRotation Maximal considered rotation between two fingerprint images. Valid range is between 0 and 180.
     * @return On return, contains match score
     */
    public int ansiVerifyMatch(final byte[] probeTemplate, final byte[] galleryTemplate, int maxRotation) {
        checkNotNull("probeTemplate", probeTemplate);
        checkNotNull("galleryTemplate", galleryTemplate);
        if (maxRotation < 0 || maxRotation > 180) {
            throw new IllegalArgumentException("Parameter maxRotation: invalid value " + maxRotation + ": Must be 0..180");
        }
        final IntByReference score = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ANSI_VerifyMatch(probeTemplate, galleryTemplate, maxRotation, score));
        return score.getValue();
    }

    /**
     * Compares specified finger views from ANSI/INCITS 378 compliant templates. <p/>
     * This function compares given finger views from ANSI/INCITS 378 compliant templates and outputs a match score. The
    score returned is an integer value ranging from 0 to 100000 which represents the similarity of original fingerprint images
    corresponding to compared finger views. See topic Matching Scores ( see page 3) for more details.
     * @param probeTemplate ANSI/INCITS 378 template
     * @param probeView Index number of the compared finger view from probe template. 0 is the first index number, 1 the second, etc.
     * @param galleryTemplate ANSI/INCITS 378 template
     * @param galleryView Index number of the compared finger view from gallery template. 0 is the first index number, 1 the second, etc.
     * @param maxRotation Maximal considered rotation between two fingerprint images. Valid range is between 0 and 180.
     * @return On return, contains match score
     */
    public int ansiVerifyMatchEx(final byte[] probeTemplate, int probeView, final byte[] galleryTemplate, int galleryView, int maxRotation) {
        checkNotNull("probeTemplate", probeTemplate);
        checkNotNull("galleryTemplate", galleryTemplate);
        if (maxRotation < 0 || maxRotation > 180) {
            throw new IllegalArgumentException("Parameter maxRotation: invalid value " + maxRotation + ": Must be 0..180");
        }
        if (probeView < 0) {
            throw new IllegalArgumentException("Parameter probeView: invalid value " + probeView + ": Must be >=0");
        }
        if (galleryView < 0) {
            throw new IllegalArgumentException("Parameter galleryView: invalid value " + galleryView + ": Must be >=0");
        }
        final IntByReference score = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ANSI_VerifyMatchEx(probeTemplate, probeView, galleryTemplate, galleryView, maxRotation, score));
        return score.getValue();
    }

    /**
     * Creates ISO/IEC 19794-2 compliant template.<p/>
     * This function takes a raw image as input and generates the corresponding ISO/IEC 19794-2 compliant fingerprint template.
    The memory for the template is allocated before the call (i.e., ISO_CreateTemplate does not handle the memory allocation
    for the template parameter).
     * @param width The number of pixels indicating the width of the image
     * @param height The number of pixels indicating the height of the image
     * @param rawImage Pointer to the uncompressed raw image for template creation
     * @return     the processed template. The maximal size of
    generated template is 1566 bytes.
     */
    public byte[] isoCreateTemplate(int width, int height, final byte[] rawImage) {
        checkNotNull("rawImage", rawImage);
        final byte[] isoTemplate = new byte[IENGINE_MAX_ISO_TEMPLATE_SIZE];
        check(AnsiIsoNative.INSTANCE.ISO_CreateTemplate(width, height, rawImage, isoTemplate));
        return isoTemplate;
    }

    /**
     * Creates ISO/IEC 19794-2 compliant template, stores intermediate images.<p/>
     * This function takes a raw image as input and generates the corresponding ISO/IEC 19794-2 compliant fingerprint template.
    It optionally stores intermediate images produced during the extraction phase.
     * @param width The number of pixels indicating the width of the image
     * @param height The number of pixels indicating the height of the image
     * @param rawImage Pointer to the uncompressed raw image for template creation
     * @param skeletonImageFile Specifies the filename of bmp image where the fingerprint skeleton image will be saved. If this parameter is NULL, no skeleton image is saved.
     * @param binarizedImageFile Specifies the filename of bmp image where the fingerprint binary image will be saved. If this parameter is NULL, no binary image is saved.
     * @param minutiaeImageFile    Specifies the filename of bmp image where the minutiae image will be saved (original fingerprint with all detected minutiae). If this parameter is NULL, no
    minutiae image is saved.
     * @return the processed template. The maximal size of
    generated templates is 1566 bytes.
     */
    public byte[] isoCreateTemplateEx(int width, int height, final byte[] rawImage, final String skeletonImageFile, final String binarizedImageFile, final String minutiaeImageFile) {
        checkNotNull("rawImage", rawImage);
        final byte[] isoTemplate = new byte[IENGINE_MAX_ISO_TEMPLATE_SIZE];
        check(AnsiIsoNative.INSTANCE.ISO_CreateTemplateEx(width, height, rawImage, isoTemplate, skeletonImageFile, binarizedImageFile, minutiaeImageFile));
        return isoTemplate;
    }

    /**
     * Compares two ISO/IEC 19794-2 compliant templates.<p/>
    This function compares two ISO/IEC 19794-2 compliant templates and outputs a match score. The score returned is an
    integer value ranging from 0 to 100000 which represents the similarity of original fingerprint images corresponding to
    compared templates. See topic Matching Scores ( see page 3) for more details.
     * @param probeTemplate ISO/IEC 19794-2 template
     * @param galleryTemplate ISO/IEC 19794-2 template
     * @param maxRotation Maximal considered rotation between two fingerprint images. Valid range is between 0 and 180.
     * @return On return, contains match score
     */
    public int isoVerifyMatch(final byte[] probeTemplate, final byte[] galleryTemplate, int maxRotation) {
        checkNotNull("probeTemplate", probeTemplate);
        checkNotNull("galleryTemplate", galleryTemplate);
        if (maxRotation < 0 || maxRotation > 180) {
            throw new IllegalArgumentException("Parameter maxRotation: invalid value " + maxRotation + ": Must be 0..180");
        }
        final IntByReference score = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_VerifyMatch(probeTemplate, galleryTemplate, maxRotation, score));
        return score.getValue();
    }

    /**
     * Compares specified finger views from ISO/IEC 19794-2 compliant templates.<p/>
     * This function compares given finger views from ISO/IEC 19794-2 compliant templates and outputs a match score. The score
    returned is an integer value ranging from 0 to 100000 which represents the similarity of original fingerprint images
    corresponding to compared finger views. See topic Matching Scores ( see page 3) for more details.
     * @param probeTemplate ISO/IEC 19794-2 template
     * @param probeView Index number of the compared finger view from probe template. 0 is the first index number, 1 the second, etc.
     * @param galleryTemplate ISO/IEC 19794-2 template
     * @param galleryView  Index number of the compared finger view from gallery template. 0 is the first index number, 1 the second, etc.
     * @param maxRotation Maximal considered rotation between two fingerprint images. Valid range is between 0 and 180.
     * @return On return, contains match score
     */
    public int isoVerifyMatchEx(final byte[] probeTemplate, int probeView, final byte[] galleryTemplate, int galleryView, int maxRotation) {
        checkNotNull("probeTemplate", probeTemplate);
        checkNotNull("galleryTemplate", galleryTemplate);
        if (maxRotation < 0 || maxRotation > 180) {
            throw new IllegalArgumentException("Parameter maxRotation: invalid value " + maxRotation + ": Must be 0..180");
        }
        if (probeView < 0) {
            throw new IllegalArgumentException("Parameter probeView: invalid value " + probeView + ": Must be >=0");
        }
        if (galleryView < 0) {
            throw new IllegalArgumentException("Parameter galleryView: invalid value " + galleryView + ": Must be >=0");
        }
        final IntByReference score = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_VerifyMatchEx(probeTemplate, probeView, galleryTemplate, galleryView, maxRotation, score));
        return score.getValue();
    }

    // Template Manipulation Functions
    /**
     * Get specific template parameters.<p/>
     * This function retrieves the value of a specific template parameter stored in record header or in finger view header of the input
    ANSI/INCITS 378 template. If specified template contains multiple finger views, this function retrieves information related to
    the first finger view (finger view with the lowest index number).
     * @param ansiTemplate ANSI/INCITS 378 template
     * @param parameter Contains the code of the template parameter
     * @return On return, contains the value of the specified parameter
     */
    public int ansiGetTemplateParameter(final byte[] ansiTemplate, TemplateParameter parameter) {
        checkNotNull("ansiTemplate", ansiTemplate);
        checkNotNull("parameter", parameter);
        final IntByReference result = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ANSI_GetTemplateParameter(ansiTemplate, parameter.cval, result));
        return result.getValue();
    }

    /**
     * Set specific template parameter.<p/>
     * This function modifies the information concerning specific template parameters stored in record header or in finger view
    header of an ANSI/INCITS 378 template. If specified template contains multiple finger views, this function modifies the first
    finger view (finger view with the lowest index number).
     * @param ansiTemplate On input, this parameter should contain a valid ANSI/INCITS 378 template. On return, the original template is modified.
     * @param parameter Contains the code of the template parameter to be set.
     * @param value Contains the value for the specified parameter
     */
    public void ansiSetTemplateParameter(byte[] ansiTemplate, TemplateParameter parameter, int value) {
        checkNotNull("ansiTemplate", ansiTemplate);
        checkNotNull("parameter", parameter);
        check(AnsiIsoNative.INSTANCE.ANSI_SetTemplateParameter(ansiTemplate, parameter.cval, value));
    }

    /**
     * Returns specified finger view from ANSI/INCITS 378 compliant template.<p/>
     * This function reads specified finger view from given ANSI/INCITS 378 compliant template and returns new ANSI/INCITS 378
    compliant template containing only single finger view. Record header of the new template is a copy of the record header of
    the input template.
     * @param ansiTemplate ANSI/INCITS 378 template
     * @param fingerView Index number of the finger view that will be returned in the newly created template. 0 is the first index number, 1 the second, etc.
     * @return resulting ANSI/INCITS 378 template containing the specified finger view. The maximal size of such
    template is 1568 bytes.
     */
    public byte[] ansiGetFingerView(final byte[] ansiTemplate, int fingerView) {
        checkNotNull("ansiTemplate", ansiTemplate);
        if (fingerView < 0) {
            throw new IllegalArgumentException("Parameter fingerView: invalid value " + fingerView + ": Must be >=0");
        }
        final byte[] result = new byte[IENGINE_MAX_ANSI_TEMPLATE_SIZE];
        check(AnsiIsoNative.INSTANCE.ANSI_GetFingerView(ansiTemplate, fingerView, result));
        return result;
    }

    /**
     * Returns bmp image with minutiae points marked over given fingerprint.<p/>
     * This function draws minutiae points associated with the given fingerprint template and returns the result as bmp image in
    memory. If inputImage is NULL, minutiae are drawn on a blank background, otherwise inputImage is used as background.
     * @param ansiTemplate ANSI/INCITS 378 compliant fingerprint template
     * @param width Width of inputImage, if inputImage is NULL, this value is ignored
     * @param height Height of inputImage, if inputImage is NULL, this value is ignored
     * @param inputImage Raw image representing fingerprint from which fingerprint template was extracted
     * @return the resulting image in bmp format
     */
    public byte[] ansiDrawMinutiae(final byte[] ansiTemplate, int width, int height, byte[] inputImage) {
        checkNotNull("ansiTemplate", ansiTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ANSI_DrawMinutiae(ansiTemplate, width, height, inputImage, null, length));
        final byte[] result = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ANSI_DrawMinutiae(ansiTemplate, width, height, inputImage, result, length));
        return result;
    }

    private static class IEngineMinutiae extends Structure {

	/**
	 * Minutia angle encoded in one byte. Valid range: 0-255.
	 */
	public byte angle;
	/**
	 * Minutiae x coordinate as stored in the template.
	 */
	public short x;
	/**
	 * Minutiae y coordinate as stored in the template.
	 */
	public short y;
	/**
	 * Minutiae type (bifurcation/ending)
	 */
	public byte type;
	public Minutiae toMinutiae() {
	    return new Minutiae(angle < 0 ? angle + 256 : angle, x, y, MinutiaeTypeEnum.fromVal(type));
	}
    }

    /**
     * Returns minutiae stored in ANSI/INCITS 378 compliant template.<p/>
     * This function returns minutiae angles and minutiae positions stored in ANSI/INCITS 378 compliant template
     * @param ansiTemplate ANSI/INCITS 378 template
     * @return Initialized minutiae array (maximal minutiae count in ANSI/INCITS 378 template is 256).
     */
    public Minutiae[] ansiGetMinutiae(final byte[] ansiTemplate) {
        checkNotNull("ansiTemplate", ansiTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ANSI_GetMinutiae(ansiTemplate, null, length));
        final IEngineMinutiae[] result = new IEngineMinutiae[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ANSI_GetMinutiae(ansiTemplate, result, length));
	final Minutiae[] r = new Minutiae[result.length];
	for (int i = 0; i < result.length; i++) {
	    r[i] = result[i].toMinutiae();
	}
	return r;
    }

    /**
     * Combines finger views from two ANSI/INCITS 378 templates into one common template.<p/>
     * This function reads finger views from two ANSI/INCITS 378 templates and merges them into one resulting template. Each
    input template may contain zero, one or multiple finger views. Record header of the resulting template is copied from the
    record header of the reference template.
     * @param referenceTemplate Reference ANSI/INCITS 378 template
     * @param addedTemplate ANSI/INCITS 378 template. Finger views from this template will be combined with finger views from the reference template.
     * @return the resulting ANSI/INCITS 378 compliant template containing all finger views from input templates
     */
    public byte[] ansiMergeTemplates(final byte[] referenceTemplate, final byte[] addedTemplate) {
        checkNotNull("referenceTemplate", referenceTemplate);
        checkNotNull("addedTemplate", addedTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ANSI_MergeTemplates(referenceTemplate, addedTemplate, length, null));
        final byte[] result = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ANSI_MergeTemplates(referenceTemplate, addedTemplate, length, result));
        return result;
    }

    /**
     * Loads ANSI/INCITS 378 compliant template from file.<p/>
     * This function loads an ANSI/INCITS 378 compliant template from file.
     * @param filename Name of the file where the template will be saved
     * @return  the ANSI/INCITS 378 compatible template. The
    maximal size of ANSI/INCITS template is 1568 bytes.
     */
    public byte[] ansiLoadTemplate(final String filename) {
        checkNotNull("filename", filename);
        final byte[] result = new byte[IENGINE_MAX_ANSI_TEMPLATE_SIZE];
        check(AnsiIsoNative.INSTANCE.ANSI_LoadTemplate(filename, result));
        return result;

    }

    /**
     * Removes minutiae points from an ANSI template.<p/>
     * This function limits maximal number of minutiae points contained in an ANSI/INCITS 378 compliant fingerprint template. If
    the input template contains more minutiae points than the provided limit, extra minutae point are removed from the template.
    The truncation is made by peeling off minutiae that are farthest from the point of gravity of the minutiae set.
     * @param inTemplate  Reference ANSI/INCITS 378 template
     * @param maximumMinutiaeCount     The maximal number of minutiae that will be stored in the output template. If current minutiae count is less or equal to this limit, the output template will be
    a copy of the input template.
     * @return resulting truncated ANSI/INCITS 378 compliant template
     */
    public byte[] ansiRemoveMinutiae(byte[] inTemplate, int maximumMinutiaeCount) {
        checkNotNull("inTemplate", inTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ANSI_RemoveMinutiae(inTemplate, maximumMinutiaeCount, length, null));
        final byte[] result = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ANSI_RemoveMinutiae(inTemplate, maximumMinutiaeCount, length, result));
        return result;
    }

    /**
     * Stores ANSI/INCITS 378 compliant template to file.<p/>
     * This function stores the input ANSI/INCITS 378 compliant template to a file
     * @param filename Name of the file where the input template will be saved
     * @param ansiTemplate ANSI/INCITS 378 template
     */
    public void ansiSaveTemplate(final String filename, final byte[] ansiTemplate) {
        checkNotNull("filename", filename);
        checkNotNull("ansiTemplate", ansiTemplate);
        check(AnsiIsoNative.INSTANCE.ANSI_SaveTemplate(filename, ansiTemplate));
    }

    /**
     * Get specific template parameters.<p/>
     * This function retrieves the value of a specific template parameter stored in record header or in finger view header of the input
    ISO/IEC 19794-2 template. If specified template contains multiple finger views, this function retrieves information related to
    the first finger view (finger view with the lowest index number).
     * @param isoTemplate ISO/IEC 19794-2 template
     * @param parameter  Contains the code of the template parameter
     * @return On return, contains the value of the specified parameter
     */
    public int isoGetTemplateParameter(final byte[] isoTemplate, TemplateParameter parameter) {
        checkNotNull("isoTemplate", isoTemplate);
        checkNotNull("parameter", parameter);
        final IntByReference result = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_GetTemplateParameter(isoTemplate, parameter.cval, result));
        return result.getValue();
    }

    /**
     * Set specific template parameter.<p/>
     * This function modifies the information concerning specific template parameters stored in record header or in finger view
    header of an ISO/IEC 19794-2 template. If specified template contains multiple finger views, this function modifies the first
    finger view (finger view with the lowest index number).
     * @param isoTemplate On input, this parameter should contain a valid ISO/IEC 19794-2 template. On return, the original template is modified
     * @param parameter Contains the code of the template parameter to be set.
     * @param value Contains the value for the specified parameter
     */
    public void isoSetTemplateParameter(byte[] isoTemplate, TemplateParameter parameter, int value) {
        checkNotNull("isoTemplate", isoTemplate);
        checkNotNull("parameter", parameter);
        check(AnsiIsoNative.INSTANCE.ISO_SetTemplateParameter(isoTemplate, parameter.cval, value));
    }

    /**
     * Returns specified finger view from ISO/IEC 19794-2 compliant template.<p/>
     * This function reads specified finger view from given ISO/IEC 19794-2 compliant template and returns new ISO/IEC 19794-2
    compliant template containing only single finger view. Record header of the new template is a copy of the record header of
    the input template.
     * @param isoTemplate ISO/IEC 19794-2 template
     * @param fingerView Index number of the finger view that will be returned in the newly created template. 0 is the first index number, 1 the second, etc.
     * @return the resulting ISO/IEC 19794-2 template containing the specified finger
     */
    public byte[] isoGetFingerView(final byte[] isoTemplate, int fingerView) {
        checkNotNull("isoTemplate", isoTemplate);
        if (fingerView < 0) {
            throw new IllegalArgumentException("Parameter fingerView: invalid value " + fingerView + ": Must be >=0");
        }
        final byte[] result = new byte[IENGINE_MAX_ISO_TEMPLATE_SIZE];
        check(AnsiIsoNative.INSTANCE.ISO_GetFingerView(isoTemplate, fingerView, result));
        return result;
    }

    /**
     * Returns bmp image with minutiae points marked over given fingerprint.<p/>
     * This function draws minutiae points associated with the given fingerprint template and returns the result as bmp image in
    memory. If inputImage is NULL, minutiae are drawn on a blank background, otherwise inputImage is used as background.
     * @param isoTemplate  ISO/IEC 19794-2 compliant fingerprint template
     * @param width Width of inputImage, if inputImage is NULL, this value is ignored
     * @param height Height of inputImage, if inputImage is NULL, this value is ignored
     * @param inputImage Raw image representing fingerprint from which fingerprint template was extracted
     * @return the resulting image, in bmp format
     */
    public byte[] isoDrawMinutiae(final byte[] isoTemplate, int width, int height, byte[] inputImage) {
        checkNotNull("isoTemplate", isoTemplate);
        final IntByReference outputImageLength = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_DrawMinutiae(isoTemplate, width, height, inputImage, null, outputImageLength));
        final byte[] result = new byte[outputImageLength.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_DrawMinutiae(isoTemplate, width, height, inputImage, result, outputImageLength));
        return result;
    }

    /**
     * Returns minutiae stored in ISO/IEC 19794-2 compliant template.<p/>
     * This function returns minutiae angles and minutiae positions stored in ISO/IEC 19794-2 compliant template
     * @param isoTemplate ISO/IEC 19794-2 template
     * @return Initialized minutiae array. Maximal minutiae count in ISO/IEC 19794-2 template is 256
     */
    public Minutiae[] isoGetMinutiae(final byte[] isoTemplate) {
        checkNotNull("isoTemplate", isoTemplate);
        final IntByReference minutiaeCount = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_GetMinutiae(isoTemplate, null, minutiaeCount));
        final IEngineMinutiae[] result = new IEngineMinutiae[minutiaeCount.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_GetMinutiae(isoTemplate, result, minutiaeCount));
	final Minutiae[] r = new Minutiae[result.length];
	for (int i = 0; i < result.length; i++) {
	    r[i] = result[i].toMinutiae();
	}
	return r;
    }

    /**
     * Combines finger views from two ISO/IEC 19794-2 templates into one common template.<p/>
     * This function reads finger views from two ISO/IEC 19794-2 templates and merges them into one resulting template. Each
    input template may contain zero, one or multiple finger views. Record header of the resulting template is copied from the
    record header of the reference template.
     * @param referenceTemplate Reference ISO/IEC 19794-2 template
     * @param addedTemplate  ISO/IEC 19794-2 template. Finger views from this template will be combined with finger views from the reference template.
     * @return  the resulting ISO/IEC 19794-2 compliant template containing all finger views from input templates
     */
    public byte[] isoMergeTemplates(final byte[] referenceTemplate, final byte[] addedTemplate) {
        checkNotNull("referenceTemplate", referenceTemplate);
        checkNotNull("addedTemplate", addedTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_MergeTemplates(referenceTemplate, addedTemplate, length, null));
        final byte[] result = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_MergeTemplates(referenceTemplate, addedTemplate, length, result));
        return result;
    }

    /**
     * Loads ISO/IEC 19794-2 compliant template from file.<p/>
     * This function loads an ISO/IEC 19794-2 compliant template from file
     * @param filename Name of the file where the template will be saved
     * @return the ISO/IEC 19794-2 compatible template. The
    maximal size of ISO/IEC 19794-2 template is 1566 bytes.
     */
    public byte[] isoLoadTemplate(final String filename) {
        checkNotNull("filename", filename);
        final byte[] result = new byte[IENGINE_MAX_ISO_TEMPLATE_SIZE];
        check(AnsiIsoNative.INSTANCE.ISO_LoadTemplate(filename, result));
        return result;
    }

    /**
     * Removes minutiae points from an ISO template.<p/>
     * This function limits maximal number of minutiae points contained in an ISO/IEC 19794-2 compliant fingerprint template. If the
    input template contains more minutiae points than the provided limit, extra minutae point are removed from the template.
    The truncation is made by peeling off minutiae that are farthest from the point of gravity of the minutiae set.
     * @param inTemplate Reference ISO/IEC 19794-2 template
     * @param maximumMinutiaeCount     The maximal number of minutiae that will be stored in the output template. If current minutiae count is less or equal to this limit, the output template will be
    a copy of the input template.
     * @return resulting truncated ISO/IEC 19794-2 compliant template
     */
    public byte[] isoRemoveMinutiae(byte[] inTemplate, int maximumMinutiaeCount) {
        checkNotNull("inTemplate", inTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_RemoveMinutiae(inTemplate, maximumMinutiaeCount, length, null));
        final byte[] result = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_RemoveMinutiae(inTemplate, maximumMinutiaeCount, length, result));
        return result;
    }

    /**
     * Stores ISO/IEC 19794-2 compliant template to file.<p/>
     * This function stores the input ISO/IEC 19794-2 compliant template to a file
     * @param filename Name of the file where the input template will be saved
     * @param isoTemplate
     */
    public void isoSaveTemplate(final String filename, final byte[] isoTemplate) {
        checkNotNull("filename", filename);
        checkNotNull("isoTemplate", isoTemplate);
        check(AnsiIsoNative.INSTANCE.ISO_SaveTemplate(filename, isoTemplate));
    }

    /**
     * Converts between different template formats.<p/>
     * This function converts an input template to an equivalent template in a format specified by user. Templates with multiple
    finger views are supported by this function. When converting to {@link IEngineTemplateFormat#ILO_SID_TEMPLATE} format, all "fixed" values are set
    according to the specification (see http://www.ilo.org/public/english/dialogue/sector/papers/maritime/sid0002.pdf Annex B for
    more details). Similarly, the resulting template will always have two fingers. If the input template contains only one (or none)
    fingerview, the resulting ILO SID template will have one (or two) "unenrolled" fingers with finger quality set to 0x65
    (enrollment failed due to a physical disability).
     * @param inputTemplateType Specifies the format of the input template
     * @param inputTemplate Reference template
     * @param outputTemplateType Specifies the format of the output template
     * @return the resulting template
     */
    public byte[] iengineConvertTemplate(IEngineTemplateFormat inputTemplateType, byte[] inputTemplate, IEngineTemplateFormat outputTemplateType) {
        checkNotNull("inputTemplateType", inputTemplateType);
        checkNotNull("inputTemplate", inputTemplate);
        checkNotNull("outputTemplateType", outputTemplateType);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.IEngine_ConvertTemplate(inputTemplateType.ordinal(), inputTemplate, outputTemplateType.ordinal(), length, null));
        final byte[] result = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.IEngine_ConvertTemplate(inputTemplateType.ordinal(), inputTemplate, outputTemplateType.ordinal(), length, result));
        return result;
    }
}
