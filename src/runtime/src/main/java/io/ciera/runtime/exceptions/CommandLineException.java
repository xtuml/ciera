package io.ciera.runtime.exceptions;

public class CommandLineException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public CommandLineException() {
        super();
    }

    public CommandLineException(String message) {
        super(message);
    }

    public CommandLineException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandLineException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CommandLineException(Throwable cause) {
        super(cause);
    }

}
