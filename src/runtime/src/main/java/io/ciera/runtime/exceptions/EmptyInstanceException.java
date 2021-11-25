package io.ciera.runtime.exceptions;

public class EmptyInstanceException extends InstancePopulationException {

    private static final long serialVersionUID = 1l;

    public EmptyInstanceException() {
        super();
    }

    public EmptyInstanceException(String message) {
        super(message);
    }

    public EmptyInstanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyInstanceException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EmptyInstanceException(Throwable cause) {
        super(cause);
    }

}
