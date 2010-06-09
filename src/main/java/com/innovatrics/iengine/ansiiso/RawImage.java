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
    public final int width;
    public final int height;
    public final byte[] raw;
}
