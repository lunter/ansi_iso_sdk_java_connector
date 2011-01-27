package com.innovatrics.iengine.ansiiso;

/**
 * Contains analyzed fingerprint images.
 * @author Martin Vysny
 */
public class FingerprintImages {
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
     * The image orientation map, scaled down {@value AnsiIso#BLOCK_SIZE_PIXELS} times relative to the fingerprint image. Not really an image -
     * each 'pixel' is really an angle 0-255, counter-clockwise; 0 = right.
     */
    public final RawImage orientation;

    public FingerprintImages(byte[] template, RawImage filteredImage, RawImage binarizedImage, RawImage skeletonImage, RawImage mask, RawImage orientation) {
	this.template = template;
	this.filteredImage = filteredImage;
	this.binarizedImage = binarizedImage;
	this.skeletonImage = skeletonImage;
	this.mask = mask;
	this.orientation = orientation;
    }
}
