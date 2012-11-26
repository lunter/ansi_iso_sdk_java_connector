package com.innovatrics.iengine.ansiiso;

/**
 * Structure representing a particular minutia (distinctive fingerprint feature found in fingerprint skeleton, such as a bifurcation or an ending).
 */
public final class Minutia {

    /**
     * Minutia angle, not null.
     */
    public final Angle angle;
    /**
     * Minutiae x coordinate as stored in the template.
     */
    public final int x;
    /**
     * Minutiae y coordinate as stored in the template.
     */
    public final int y;
    /**
     * Minutiae type (bifurcation/ending), not null.
     */
    public final MinutiaeTypeEnum type;

    public Minutia(Angle angle, int x, int y, MinutiaeTypeEnum type) {
        AnsiIso.checkNotNull("angle", angle);
        AnsiIso.checkNotNull("type", type);
	this.angle = angle;
	this.x = x;
	this.y = y;
	this.type = type;
    }

    @Override
    public String toString() {
	return "Minutia{" + type + " at " + x + "," + y + "; angle=" + angle + '}';
    }
}
