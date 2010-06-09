package com.innovatrics.iengine.ansiiso;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

/**
 * JNA bindings for the IEngine ANSI-ISO API.
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

        final AnsiIsoNative INSTANCE = (AnsiIsoNative) Native.loadLibrary("AnsiIso", AnsiIsoNative.class); // NOI18N
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

        int IEngine_LoadBMP(final byte[] filename, IntByReference width, IntByReference height, byte[] rawImage, IntByReference length);

        int IEngine_ConvertBMP(final byte[] bmpImage, IntByReference width, IntByReference height, byte[] rawImage, IntByReference length);

        // Template Extraction and Matching Functions
        int ANSI_CreateTemplate(int width, int height, final byte[] rawImage, byte[] ansiTemplate);

        int ANSI_CreateTemplateEx(int width, int height, final byte[] rawImage, byte[] ansiTemplate, final /*char*/ byte[] skeletonImageFile, final /*char*/ byte[] binarizedImageFile, final /*char*/ byte[] minutiaeImageFile);

        int ANSI_VerifyMatch(final byte[] probeTemplate, final byte[] galleryTemplate, int maxRotation, IntByReference score);

        int ANSI_VerifyMatchEx(final byte[] probeTemplate, int probeView, final byte[] galleryTemplate, int galleryView, int maxRotation, IntByReference score);

        int ISO_CreateTemplate(int width, int height, final byte[] rawImage, byte[] isoTemplate);

        int ISO_CreateTemplateEx(int width, int height, final byte[] rawImage, byte[] isoTemplate, final /*char*/ byte[] skeletonImageFile, final /*char*/ byte[] binarizedImageFile, final /*char*/ byte[] minutiaeImageFile);

        int ISO_VerifyMatch(final byte[] probeTemplate, final byte[] galleryTemplate, int maxRotation, IntByReference score);

        int ISO_VerifyMatchEx(final byte[] probeTemplate, int probeView, final byte[] galleryTemplate, int galleryView, int maxRotation, IntByReference score);
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
        check(AnsiIsoNative.INSTANCE.IEngine_LoadBMP(filename.getBytes(), width, height, null, length));
        final byte[] rawImage = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.IEngine_LoadBMP(filename.getBytes(), width, height, rawImage, length));
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
        check(AnsiIsoNative.INSTANCE.ANSI_CreateTemplateEx(width, height, rawImage, result, skeletonImageFile == null ? null : skeletonImageFile.getBytes(), binarizedImageFile == null ? null : binarizedImageFile.getBytes(), minutiaeImageFile == null ? null : minutiaeImageFile.getBytes()));
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
        check(AnsiIsoNative.INSTANCE.ISO_CreateTemplateEx(width, height, rawImage, isoTemplate, skeletonImageFile == null ? null : skeletonImageFile.getBytes(), binarizedImageFile == null ? null : binarizedImageFile.getBytes(), minutiaeImageFile == null ? null : minutiaeImageFile.getBytes()));
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
    public int ISO_VerifyMatchEx(final byte[] probeTemplate, int probeView, final byte[] galleryTemplate, int galleryView, int maxRotation) {
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
}
