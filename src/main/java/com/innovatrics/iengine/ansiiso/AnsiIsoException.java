package com.innovatrics.iengine.ansiiso;

/**
 * Exception thrown by the {@link AnsiIso} wrapper methods.
 * @author Martin Vysny
 */
public class AnsiIsoException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public final int errorCode;

    public AnsiIsoException(String message, final int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
