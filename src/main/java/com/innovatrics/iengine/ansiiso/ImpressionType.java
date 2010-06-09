package com.innovatrics.iengine.ansiiso;

/*
 * Defines impression type of fingerprint image
 */
public enum ImpressionType {

    TYPE_LIVE_SCAN_PLAIN(0),
    TYPE_LIVE_SCAN_ROLLED(1),
    TYPE_NONLIVE_SCAN_PLAIN(2),
    TYPE_NONLIVE_SCAN_ROLLED(3),
    TYPE_SWIPE(4),
    TYPE_LIVE_SCAN_CONTACTLESS(9);
    public final int cval;

    private ImpressionType(int cval) {
        this.cval = cval;
    }

    public static ImpressionType fromVal(int cval) {
        for (ImpressionType it : values()) {
            if (it.cval == cval) {
                return it;
            }
        }
        throw new IllegalArgumentException("Parameter cval: invalid value " + cval + ": Undefined value");
    }
}
