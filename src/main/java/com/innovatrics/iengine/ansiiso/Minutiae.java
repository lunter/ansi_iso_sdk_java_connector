package com.innovatrics.iengine.ansiiso;

/**
 * Structure representing a particular minutia (distinctive fingerprint feature found in fingerprint skeleton, such as a bifurcation or an ending).
 */
public final class Minutiae {

    /**
     * Minutia angle encoded in one byte. Valid range: 0-255. A clockwise angle, where:
     * <ul><li>0 = right</li><li>64 = bottom</li><li>128 = left</li><li>196 = top</li></ul>
     */
    public final int angle;
    /**
     * Returns a counter-clockwise angle 0-359 degree.
     * @return counter-clockwise angle 0-359 degree.
     */
    public int getAngleInDegree() {
	return ((256 - angle) * 360) / 256;
    }
    /**
     * Returns a counter-clockwise radian angle 0-2pi.
     * @return counter-clockwise radian angle 0-2pi.
     */
    public double getRadianAngle() {
	return Math.PI * getAngleInDegree() / 180;
    }
    /**
     * Minutiae x coordinate as stored in the template.
     */
    public final int x;
    /**
     * Minutiae y coordinate as stored in the template.
     */
    public final int y;
    /**
     * Minutiae type (bifurcation/ending)
     */
    public final MinutiaeTypeEnum type;

    public Minutiae(int angle, int x, int y, MinutiaeTypeEnum type) {
	this.angle = angle;
	this.x = x;
	this.y = y;
	this.type = type;
    }

    @Override
    public String toString() {
	return "Minutiae{" + type + " at " + x + "," + y + "; angle=" + angle + '}';
    }
}
