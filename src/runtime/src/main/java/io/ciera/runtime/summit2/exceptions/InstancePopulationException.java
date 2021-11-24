package io.ciera.runtime.summit2.exceptions;

public class InstancePopulationException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public InstancePopulationException() {
        super();
    }

    public InstancePopulationException(String message) {
        super(message);
    }

    public InstancePopulationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstancePopulationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InstancePopulationException(Throwable cause) {
        super(cause);
    }

}
