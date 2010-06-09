package com.innovatrics.iengine.ansiiso;

/**
 * Enumeration defining codes for parameters contained in ISO/IEC 19794-2 and ANSI/INCITS 378 compliant templates.
 */
public enum TemplateParameter {

    /**
     * Specifies the 'owner' of the encoding application. This value is read-only (cannot be used with IEngine_SetTemplateParameter function).
     */
    PARAM_PRODUCT_OWNER(0),
    /**
     * Specifies the version of the encoding application. This value is read-only (cannot be used with IEngine_SetTemplateParameter function).
     */
    PARAM_PRODUCT_VERSION(1),
    /**
     * Specifies the total length of the template in bytes. This value is read-only (cannot be used with IEngine_SetTemplateParameter function).
     */
    PARAM_TEMPLATE_SIZE(2),
    /**
     * Shall be a 4-bit value between 0 and 15, the most significant bit, if set to a 1,
     * indicates that the equipment was cerified to comply with Appendix F
     * (IAFIS Image Quality Specification, January 29, 1999) of FJIS-RS-0010,
     * the Federal Bureau of Investigations's Electronic Fingerprint Transmission Specification.
     * The other three bits are reserved for future compliance indicators.
     * The default value for this parameter is 0.
     */
    PARAM_CAPTURE_EQUIPMENT_COMPLIANCE(3),
    /**
     * Shall be recorded in twelve bits.
     * A value of all zeros are acceptable and idicate that the capture equipment ID is unreported.
     * In other case, the value of the field is detemined by the vendor.
     * The default value for this parameter is 0.
     */
    PARAM_CAPTURE_EQUIPMENT_ID(4),
    /**
     * Specifies total number of finger views contained within given template.
     * This value is read-only (cannot be used with IEngine_SetTemplateParameter function).
     */
    PARAM_FINGER_VIEW_COUNT(5),
    /**
     * Specifies the finger position of the encoded fingerprint.
     * The values of different finger positions are defined in <link IENGINE_FINGER_POSITION> enum.
     * The default value for this parameter is 0 (UNKNOWN_FINGER).
     */
    PARAM_FINGER_POSITION(10),
    /**
     * Specifies the impression type of the encoded fingerprint.
     * The values of different finger positions are defined in <link IENGINE_IMPRESSION_TYPE> enum.
     * The default value for this parameter is 0 (TYPE_LIVE_SCAN_PLAIN).
     */
    PARAM_IMPRESSION_TYPE(11),
    /**
     * Specifies the quality of the encoded fingerprint.
     * This quality number is an overall expression of the quality of the finger record, and represents quality of the original image.
     * A value of 0 represents the lowest possible quality and the value 100 represents the highest possible quality. The numeric
     * values in this field are set in accordance with the general guidelines contained in Section 2.1.42 of
     * ANSI/INCITS 358.
     * The default value for this parameter is 40 (fair quality).
     */
    PARAM_FINGER_QUALITY(12);
    public final int cval;

    private TemplateParameter(int cval) {
        this.cval = cval;
    }

    public static TemplateParameter fromVal(int cval) {
        for (TemplateParameter it : values()) {
            if (it.cval == cval) {
                return it;
            }
        }
        throw new IllegalArgumentException("Parameter cval: invalid value " + cval + ": Undefined value");
    }
}
