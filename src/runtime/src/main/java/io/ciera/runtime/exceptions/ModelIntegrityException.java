package io.ciera.runtime.exceptions;

public class ModelIntegrityException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public ModelIntegrityException() {
        super();
    }

    public ModelIntegrityException(String message) {
        super(message);
    }

    public ModelIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelIntegrityException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ModelIntegrityException(Throwable cause) {
        super(cause);
    }

}
