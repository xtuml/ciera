package io.ciera.runtime.exceptions;

public class DeserializationException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserializationException(String message) {
        super(message);
    }

    public DeserializationException(Throwable cause) {
        super(cause);
    }
    
}
