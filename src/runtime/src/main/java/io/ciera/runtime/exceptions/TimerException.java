package io.ciera.runtime.exceptions;

public class TimerException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public TimerException() {
        super();
    }

    public TimerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TimerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimerException(String message) {
        super(message);
    }

    public TimerException(Throwable cause) {
        super(cause);
    }

}
