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
    public final RawImage filteredImage;
    /**
     * The fingerprint binary image.
     */
    public final RawImage binarizedImage;
    /**
     * The fingerprint skeleton image.
     */
    public final RawImage skeletonImage;
    public final RawImage mask;
    public final RawImage orientation;
    public final RawImage quality;

    public FingerprintImages(byte[] template, RawImage filteredImage, RawImage binarizedImage, RawImage skeletonImage, RawImage mask, RawImage orientation, RawImage quality) {
	this.template = template;
	this.filteredImage = filteredImage;
	this.binarizedImage = binarizedImage;
	this.skeletonImage = skeletonImage;
	this.mask = mask;
	this.orientation = orientation;
	this.quality = quality;
    }
}
