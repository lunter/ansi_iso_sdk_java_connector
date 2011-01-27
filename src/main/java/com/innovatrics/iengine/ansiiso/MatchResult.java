package com.innovatrics.iengine.ansiiso;

import java.util.Collections;
import java.util.List;

/**
 * Contains a match verification result.
 * @author Martin Vysny
 */
public class MatchResult {
    /**
     * Quality score, 0..100.
     */
    public final int score;
    public final int dx;
    public final int dy;
    /**
     * Rotation angle.
     */
    public final Angle rotation;
    public final List<Match> minutiaMatches;

    public MatchResult(int score, int dx, int dy, Angle rotation, List<Match> minutiaMatches) {
	this.score = score;
	this.dx = dx;
	this.dy = dy;
	this.rotation = rotation;
	this.minutiaMatches = Collections.unmodifiableList(minutiaMatches);
    }

    public static class Match {
	/**
	 * Index of the minutia in the minutia array for the 'probe' fingerprint.
	 */
	public final int probeMinutiaIndex;
	/**
	 * Index of the minutia in the minutia array for the 'gallery' fingerprint.
	 */
	public final int galleryMinutiaIndex;
	/**
	 * Quality score of the match, 0..100.
	 */
	public final int quality;

	public Match(int probeMinutiaIndex, int galleryMinutiaIndex, int quality) {
	    this.probeMinutiaIndex = probeMinutiaIndex;
	    this.galleryMinutiaIndex = galleryMinutiaIndex;
	    this.quality = quality;
	}
    }
}
