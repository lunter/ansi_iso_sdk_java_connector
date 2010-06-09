package com.innovatrics.iengine.ansiiso;

/**
 * Structure representing a particular minutia (distinctive fingerprint feature found in fingerprint skeleton, such as a bifurcation or an ending).
 */
public class IEngineMinutiae {
    /**
     * Minutia angle encoded in one byte. Valid range: 0-255.
     */
    public byte angle;
    /**
     * Minutiae x coordinate as stored in the template.
     */
    public short x;
    /**
     * Minutiae y coordinate as stored in the template.
     */
    public short y;
    /**
     * Minutiae type (bifurcation/ending)
     */
    public byte type;
}
