package com.innovatrics.iengine.ansiiso;

/**
 * @author Martin Vysny
 */
public class RawImage {

    public RawImage(int width, int height, byte[] raw) {
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
     * Pointer to memory space where raw image is stored.
     */
    public final byte[] raw;
}
