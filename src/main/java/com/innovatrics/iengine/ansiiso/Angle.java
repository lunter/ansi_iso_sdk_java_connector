package com.innovatrics.iengine.ansiiso;

/**
 * Represents an angle, in various units.
 * @author Martin Vysny
 */
public final class Angle {

    /**
     * Valid range: 0-255. A clockwise angle, where:
     * <ul><li>0 = right</li><li>64 = bottom</li><li>128 = left</li><li>196 = top</li></ul>
     * Use {@link #getDegree()} and/or {@link #getRadian()} to retrieve the standardized angle.
     */
    public final byte angle;
    private Integer degree = null;

    /**
     * Returns a counter-clockwise angle 0-359 degree, 0 = right.
     * @return counter-clockwise angle 0-359 degree.
     */
    public int getDegree() {
	if (degree == null) {
	    degree = (((256 - toInt(angle)) * 360) / 256) % 360;
	}
	return degree;
    }
    private Double radian = null;

    /**
     * Converts an angle in radians, to an angle in degrees.
     * @param rad angle in radians
     * @return angle in degrees, -180 .. 180
     */
    public static int radToDeg(double rad) {
	int angle = (int) (rad * 180 / Math.PI);
	angle = angle % 360;
	if (angle < -180) {
	    angle += 360;
	}
	if (angle > 180) {
	    angle -= 360;
	}
	return angle;
    }

    /**
     * Returns a counter-clockwise radian angle 0-2pi; 0 = right.
     * @return counter-clockwise radian angle 0-2pi.
     */
    public double getRadian() {
	if (radian == null) {
	    radian = Math.PI * getDegree() / 180;
	}
	return radian;
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
	return "Angle{" + getDegree() + '}';
    }
}
