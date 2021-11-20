package io.ciera.runtime.summit2.exceptions;

public class DeviceReadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeviceReadException() {
        super();
    }

    public DeviceReadException(String message) {
        super(message);
    }

    public DeviceReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
    public DeviceReadException(Throwable cause) {
        super(cause);
    }

}
