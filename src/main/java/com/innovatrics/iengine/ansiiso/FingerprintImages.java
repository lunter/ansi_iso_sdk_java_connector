package com.innovatrics.iengine.ansiiso;

import java.awt.Dimension;

/**
 * Contains analyzed fingerprint images.
 * @author Martin Vysny
 */
public final class FingerprintImages {

    /**
     * Returns the size of the fingerprint image.
     * @return the size of the fingerprint image.
     */
    public Dimension getSize() {
        return filteredImage.getSize();
    }
    
    /**
     * The ISO or ANSI template. The maximum size of ISO image is {@value AnsiIso#IENGINE_MAX_ISO_TEMPLATE_SIZE} bytes. The maximum size of ANSI image is {@value AnsiIso#IENGINE_MAX_ANSI_TEMPLATE_SIZE} bytes.
     */
    public final byte[] template;
    /**
     * The filtered fingerprint image.
     */
    public final RawImage filteredImage;
    /**
     * The fingerprint binary image.
     */
    public final RawImage binarizedImage;
    /**
     * The fingerprint skeleton image.
     */
    public final RawImage skeletonImage;
    /**
     * The image relevancy mask, scaled down {@value AnsiIso#BLOCK_SIZE_PIXELS} times relative to the fingerprint image.
     * A boolean map: zero value means non-fingerprint block, non-zero value means block which contains a fingerprint part.
     */
    public final RawImage mask;
    /**
     * The image orientation map, scaled down {@value AnsiIso#BLOCK_SIZE_PIXELS} times relative to the fingerprint image.
     */
    public final Orientations orientation;

    /**
     * The image orientation map, scaled down {@value AnsiIso#BLOCK_SIZE_PIXELS} times relative to the fingerprint image.
     * To retrieve the angle value use the {@link #getAngle(int, int)} function.
     */
    public final static class Orientations {

	/**
	 * Creates an orientation image data.
	 * @param width Contains the width of converted image, in pixels.
	 * @param height Contains the height of converted image, in pixels.
	 * @param raw the raw orientation data.
	 */
	public Orientations(int width, int height, byte[] raw) {
	    this.width = width;
	    this.height = height;
	    this.raw = raw;
	}
	/**
	 * Contains the width of converted image, in pixels.
	 */
	public final int width;
	/**
	 * Contains the height of converted image, in pixels.
	 */
	public final int height;
	/**
	 * The raw image data. To retrieve a single pixel value, use the following formula:
	 * <code>raw[y * width + x]</code>
	 */
	public final byte[] raw;

	@Override
	public String toString() {
	    return "Orientation{" + width + "x" + height + '}';
	}

	/**
	 * Returns the raw angle value. A clock-wise angle 0..255.
	 * @param x the x block coordinate.
	 * @param y the y block coordinate.
	 * @return the angle.
	 */
	public byte getRaw(int x, int y) {
	    return raw[y * width + x];
	}

	/**
	 * Returns the orientation angle for given block (see {@link Orientations} for details on blocks). Returns the counter-clockwise radian angle (0 pointing to the right), using the following formula:
	 * <pre>angle = -new Angle(getRaw(x, y)).getRadian() / 2;</pre>
	 * @param x the x block coordinate.
	 * @param y the y block coordinate.
	 * @return the counter-clockwise radian angle (0 pointing to the right)
	 */
	public Angle getAngle(int x, int y) {
	    return new Angle((byte) (-getRaw(x, y) / 2));
	}
    }

    public FingerprintImages(byte[] template, RawImage filteredImage, RawImage binarizedImage, RawImage skeletonImage, RawImage mask, Orientations orientation) {
	this.template = template;
	this.filteredImage = filteredImage;
	this.binarizedImage = binarizedImage;
	this.skeletonImage = skeletonImage;
	this.mask = mask;
	this.orientation = orientation;
    }
}
