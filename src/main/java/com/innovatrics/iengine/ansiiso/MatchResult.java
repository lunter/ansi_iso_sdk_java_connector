package com.innovatrics.iengine.ansiiso;

/**
 * Contains a match verification result.
 * @author Martin Vysny
 */
public class MatchResult {
    public final int score;
    public final int dx;
    public final int dy;
    public final int rotation;
    public final int associationCount;
    public final byte[] assocProbeMinutiae;
    public final byte[] assocGalleryMinutiae;
    public final byte[] assocQuality;

    public MatchResult(int score, int dx, int dy, int rotation, int associationCount, byte[] assocProbeMinutiae, byte[] assocGalleryMinutiae, byte[] assocQuality) {
	this.score = score;
	this.dx = dx;
	this.dy = dy;
	this.rotation = rotation;
	this.associationCount = associationCount;
	this.assocProbeMinutiae = assocProbeMinutiae;
	this.assocGalleryMinutiae = assocGalleryMinutiae;
	this.assocQuality = assocQuality;
    }
}
