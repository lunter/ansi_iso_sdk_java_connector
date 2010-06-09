package com.innovatrics.iengine.ansiiso;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * JNA bindings for the IEngine ANSI-ISO API.
 * @author Martin Vysny
 */
public class AnsiIso {

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

        public int IEngine_Init();

        public int IEngine_Terminate();

        public int IEngine_GetVersion(IEngineVersion version);

        public String IEngine_GetErrorMessage(int errcode);

        public int IEngine_SetLicenseContent(byte[] licenseContent, int length);
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
}
