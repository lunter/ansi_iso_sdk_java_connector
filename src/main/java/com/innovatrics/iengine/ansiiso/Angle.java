package com.innovatrics.iengine.ansiiso;

/**
 * Represents an angle.
 * @author Martin Vysny
 */
public class Angle {

    /**
     * Valid range: 0-255. A clockwise angle, where:
     * <ul><li>0 = right</li><li>64 = bottom</li><li>128 = left</li><li>196 = top</li></ul>
     * Use {@link #getAngleInDegree()} and/or {@link #getRadianAngle()} to retrieve the standardized angle.
     */
    public final byte angle;

    /**
     * Returns a counter-clockwise angle 0-359 degree, 0 = right.
     * @return counter-clockwise angle 0-359 degree.
     */
    public int getAngleInDegree() {
	return ((256 - toInt(angle)) * 360) / 256;
    }

    /**
     * Returns a counter-clockwise radian angle 0-2pi; 0 = right.
     * @return counter-clockwise radian angle 0-2pi.
     */
    public double getRadianAngle() {
	return Math.PI * getAngleInDegree() / 180;
    }

    private static int toInt(byte unsigned) {
	return unsigned < 0 ? 256 + unsigned : unsigned;
    }

    /**
     * Creates new angle.
     * @param angle Valid range: 0-255. A clockwise angle, where 0 = right, 64 = bottom 128 = left, 196 = top
     */
    public Angle(byte angle) {
	this.angle = angle;
    }

    @Override
    public String toString() {
	return "Angle{" + getAngleInDegree() + '}';
    }
}
