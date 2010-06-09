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
    public void init() {
        check(AnsiIsoNative.INSTANCE.IEngine_Init());
    }

    public void terminate() {
        check(AnsiIsoNative.INSTANCE.IEngine_Terminate());
    }

    public IEngineVersion getVersion(IEngineVersion version) {
        final IEngineVersion result = new IEngineVersion();
        check(AnsiIsoNative.INSTANCE.IEngine_GetVersion(result));
        return result;
    }

    public void setLicenseContent(byte[] licenseContent, int length) {
        check(AnsiIsoNative.INSTANCE.IEngine_SetLicenseContent(licenseContent, length));
    }

    // Conversion Functions
    public byte[] ansiConvertToISO(byte[] ansiTemplate) {
        checkNotNull("ansiTemplate", ansiTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ANSI_ConvertToISO(ansiTemplate, length, null));
        final byte[] isoTemplate = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ANSI_ConvertToISO(ansiTemplate, length, isoTemplate));
        return isoTemplate;
    }

    public byte[] isoConvertToANSI(byte[] isoTemplate) {
        checkNotNull("isoTemplate", isoTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_ConvertToANSI(isoTemplate, length, null));
        final byte[] ansiTemplate = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_ConvertToANSI(isoTemplate, length, ansiTemplate));
        return ansiTemplate;
    }

    public byte[] isoConvertToISOCardCC(byte[] isoTemplate, int maximumMinutiaeCount, SortOrder minutiaeOrder, SortOrder minutiaeSecondaryOrder) {
        checkNotNull("isoTemplate", isoTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_ConvertToISOCardCC(isoTemplate, maximumMinutiaeCount, minutiaeOrder.ordinal(), minutiaeSecondaryOrder.ordinal(), length, null));
        final byte[] isoCCTemplate = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_ConvertToISOCardCC(isoTemplate, maximumMinutiaeCount, minutiaeOrder.ordinal(), minutiaeSecondaryOrder.ordinal(), length, isoCCTemplate));
        return isoCCTemplate;
    }

    public byte[] isoCardCCConvertToISO(byte[] isoCCTemplate) {
        checkNotNull("isoCCTemplate", isoCCTemplate);
        final IntByReference length = new IntByReference();
        check(AnsiIsoNative.INSTANCE.ISO_CARD_CC_ConvertToISO(isoCCTemplate, length, null));
        final byte[] isoTemplate = new byte[length.getValue()];
        check(AnsiIsoNative.INSTANCE.ISO_CARD_CC_ConvertToISO(isoCCTemplate, length, isoTemplate));
        return isoTemplate;
    }

    public int getImageQuality(int width, int height, final byte[] rawImage) {
        checkNotNull("rawImage", rawImage);
        final IntByReference quality = new IntByReference();
        check(AnsiIsoNative.INSTANCE.IEngine_GetImageQuality(width, height, rawImage, quality));
        return quality.getValue();
    }

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
}
