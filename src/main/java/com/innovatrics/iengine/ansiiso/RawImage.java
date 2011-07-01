package com.innovatrics.iengine.ansiiso;

import java.awt.Dimension;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

/**
 * Represents a RAW 8-bit greyscale image data. 0 = black, 255 = white.
 * @author Martin Vysny
 */
public final class RawImage {

    /**
     * Creates a RAW image data.
     * @param width Contains the width of converted image, in pixels.
     * @param height Contains the height of converted image, in pixels.
     * @param raw the raw image data.
     */
    public RawImage(int width, int height, byte[] raw) {
	this.width = width;
	this.height = height;
	this.raw = raw;
        if (raw.length < width * height) {
            throw new IllegalArgumentException("Parameter raw: invalid size: expected " + width * height + " but got " + raw.length);
        }
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
	return "RawImage{" + width + "x" + height + '}';
    }

    public BufferedImage toBufferedImage() {
	final SampleModel sm = DEFAULT_COLOR_MODEL.createCompatibleSampleModel(width, height);
	final DataBufferByte db = new DataBufferByte(raw, width * height);
	final WritableRaster raster = Raster.createWritableRaster(sm, db, null);
	final BufferedImage result = new BufferedImage(DEFAULT_COLOR_MODEL, raster, false, null);
	return result;
    }
    private static final ColorModel DEFAULT_COLOR_MODEL = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_GRAY), new int[]{8}, false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

    /**
     * Returns a pixel located on x,y coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the pixel value, 0 = black, 127 = gray, 255 = white.
     */
    public byte getPixel(int x, int y) {
	return raw[y * width + x];
    }

    /**
     * Returns size of the image.
     * @return the dimension object.
     * @deprecated renamed to {@link #getSize()}.
     */
    @Deprecated
    public Dimension getDimension() {
	return getSize();
    }

    /**
     * Returns size of the image.
     * @return the dimension object.
     */
    public Dimension getSize() {
	return new Dimension(width, height);
    }

    /**
     * Inverts colors of this image.
     */
    public void invert() {
	for (int i = 0; i < raw.length; i++) {
	    raw[i] = (byte) (raw[i] ^ 0xFF);
	}
    }
}
