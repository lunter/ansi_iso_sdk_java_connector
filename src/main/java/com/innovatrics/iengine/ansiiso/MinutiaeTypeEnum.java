package com.innovatrics.iengine.ansiiso;

/**
 * Denotes minutiae type.
 * @author Martin Vysny
 */
public enum MinutiaeTypeEnum {

    Bifurcation(0), Ending(1);
    public final int cval;

    private MinutiaeTypeEnum(int cval) {
	this.cval = cval;
    }

    public static MinutiaeTypeEnum fromVal(int cval) {
	for (MinutiaeTypeEnum it : values()) {
	    if (it.cval == cval) {
		return it;
	    }
	}
	throw new IllegalArgumentException("Parameter cval: invalid value " + cval + ": Undefined value");
    }
}
