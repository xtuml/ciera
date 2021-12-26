package io.ciera.runtime.exceptions;

public class TerminatorException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public TerminatorException() {
        super();
    }

    public TerminatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TerminatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TerminatorException(String message) {
        super(message);
    }

    public TerminatorException(Throwable cause) {
        super(cause);
    }

}
