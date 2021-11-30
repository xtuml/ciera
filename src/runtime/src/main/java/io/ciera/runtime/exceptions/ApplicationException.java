package io.ciera.runtime.exceptions;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

}
