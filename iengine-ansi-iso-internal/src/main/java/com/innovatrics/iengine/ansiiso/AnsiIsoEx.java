package com.innovatrics.iengine.ansiiso;

import com.innovatrics.iengine.ansiiso.FingerprintImages.Orientations;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Obsahuje interne metody, iba pre potreby Innovatrics.
 *
 * @author Martin Vysny
 */
public class AnsiIsoEx extends AnsiIso {

    private static interface AnsiIsoNative extends Library {

        final AnsiIsoNative INSTANCE = (AnsiIsoNative) Native.loadLibrary("iengine_ansi_iso", AnsiIsoNative.class); // NOI18N

        int ISO_VerifyMatchEx2(final byte[] probeTemplate, int probeView, final byte[] galleryTemplate, int galleryView, int maxRotation, IntByReference score, IntByReference dx, IntByReference dy, IntByReference rotation, IntByReference associationCount, byte[] assocProbeMinutiae, byte[] assocGalleryMinutiae, byte[] assocQuality);

        int ISO_CreateTemplateEx2(int width, int height, final byte[] rawImage, byte[] isoTemplate, byte[] filteredImage, byte[] binarizedImage, byte[] skeletonImage, IntByReference blockWidth, IntByReference blockHeight, byte[] bMask, byte[] bOrientation, byte[] bQuality);
    }

    /**
     * Creates ISO/IEC 19794-2 compliant template, stores intermediate
     * images.<p/>
     * This function takes a raw image as input and generates the corresponding
     * ISO/IEC 19794-2 compliant fingerprint template. It optionally stores
     * intermediate images produced during the extraction phase.
     *
     * @param rawImage Pointer to the uncompressed raw image for template
     * creation minutiae image is saved.
     * @return the processed template. The maximal size of generated templates
     * is {@value #IENGINE_MAX_ISO_TEMPLATE_SIZE} bytes.
     */
    public FingerprintImages isoCreateTemplateEx2(final RawImage rawImage) {
        checkNotNull("rawImage", rawImage);
        final int expectedBlockWidth = (rawImage.width - 1) / BLOCK_SIZE_PIXELS + 1;
        final IntByReference blockWidth = new IntByReference(expectedBlockWidth);
        final int expectedBlockHeight = (rawImage.height - 1) / BLOCK_SIZE_PIXELS + 1;
        final IntByReference blockHeight = new IntByReference(expectedBlockHeight);
        final byte[] isoTemplate = new byte[IENGINE_MAX_ISO_TEMPLATE_SIZE];
        final int imageSize = rawImage.width * rawImage.height;
        final byte[] filteredImage = new byte[imageSize];
        final byte[] binarizedImage = new byte[imageSize];
        final byte[] skeletonImage = new byte[imageSize];
        final int maskSize = expectedBlockWidth * expectedBlockHeight;
        final byte[] mask = new byte[maskSize];
        final byte[] orientation = new byte[maskSize];
        // ignore quality map for now, it is not yet implemented.
        check(AnsiIsoNative.INSTANCE.ISO_CreateTemplateEx2(rawImage.width, rawImage.height, rawImage.raw, isoTemplate, filteredImage, binarizedImage, skeletonImage, blockWidth, blockHeight, mask, orientation, null));
        if (blockWidth.getValue() != expectedBlockWidth) {
            throw new RuntimeException("Expected blockwidth " + expectedBlockWidth + " but got " + blockWidth.getValue());
        }
        if (blockHeight.getValue() != expectedBlockHeight) {
            throw new RuntimeException("Expected blockwidth " + expectedBlockHeight + " but got " + blockHeight.getValue());
        }
        return new FingerprintImages(isoTemplate, new RawImage(rawImage.width, rawImage.height, filteredImage), new RawImage(rawImage.width, rawImage.height, binarizedImage),
                new RawImage(rawImage.width, rawImage.height, skeletonImage), new RawImage(blockWidth.getValue(), blockHeight.getValue(), mask),
                new Orientations(blockWidth.getValue(), blockHeight.getValue(), orientation));
    }

    /**
     * Compares specified finger views from ISO/IEC 19794-2 compliant
     * templates.<p/>
     * This function compares given finger views from ISO/IEC 19794-2 compliant
     * templates and outputs a match score. The score returned is an integer
     * value ranging from 0 to 100000 which represents the similarity of
     * original fingerprint images corresponding to compared finger views. See
     * topic Matching Scores ( see page 3) for more details.
     *
     * @param probeTemplate ISO/IEC 19794-2 template
     * @param probeView Index number of the compared finger view from probe
     * template. 0 is the first index number, 1 the second, etc.
     * @param galleryTemplate ISO/IEC 19794-2 template
     * @param galleryView Index number of the compared finger view from gallery
     * template. 0 is the first index number, 1 the second, etc.
     * @param maxRotation Maximal considered rotation between two fingerprint
     * images. Valid range is between 0 and 180.
     * @return On return, contains the match result information, never null.
     */
    public MatchResult isoVerifyMatchEx2(final byte[] probeTemplate, int probeView, final byte[] galleryTemplate, int galleryView, int maxRotation) {
        checkNotNull("probeTemplate", probeTemplate);
        checkNotNull("galleryTemplate", galleryTemplate);
        if (maxRotation < 0 || maxRotation > 180) {
            throw new IllegalArgumentException("Parameter maxRotation: invalid value " + maxRotation + ": Must be 0..180");
        }
        if (probeView < 0) {
            throw new IllegalArgumentException("Parameter probeView: invalid value " + probeView + ": Must be >=0");
        }
        if (galleryView < 0) {
            throw new IllegalArgumentException("Parameter galleryView: invalid value " + galleryView + ": Must be >=0");
        }
        final IntByReference score = new IntByReference();
        final IntByReference dx = new IntByReference();
        final IntByReference dy = new IntByReference();
        final IntByReference rotation = new IntByReference();
        final IntByReference associationCount = new IntByReference();
        final byte[] assocProbeMinutiae = new byte[MAX_MINUTIAE_POINTS];
        final byte[] assocGalleryMinutiae = new byte[MAX_MINUTIAE_POINTS];
        final byte[] assocQuality = new byte[MAX_MINUTIAE_POINTS];
        check(AnsiIsoNative.INSTANCE.ISO_VerifyMatchEx2(probeTemplate, probeView, galleryTemplate, galleryView, maxRotation, score, dx, dy, rotation, associationCount, assocProbeMinutiae, assocGalleryMinutiae, assocQuality));
        final List<MatchResult.Match> minutiaMatches = new ArrayList<MatchResult.Match>(associationCount.getValue());
        for (int i = 0; i < associationCount.getValue(); i++) {
            minutiaMatches.add(new MatchResult.Match(assocProbeMinutiae[i], assocGalleryMinutiae[i], assocQuality[i]));
        }
        return new MatchResult(score.getValue(), dx.getValue(), dy.getValue(), new Angle((byte) rotation.getValue()), minutiaMatches);
    }
}
