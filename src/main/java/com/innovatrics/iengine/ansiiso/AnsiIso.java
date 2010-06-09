package com.innovatrics.iengine.ansiiso;

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
}
