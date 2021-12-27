package io.ciera.runtime.summit2.exceptions;

public class DeserializationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeserializationException() {
        super();
    }

    public DeserializationException(String message) {
        super(message);
    }

    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DeserializationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public DeserializationException(Throwable cause) {
        super(cause);
    }

}
