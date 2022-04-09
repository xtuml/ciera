package io.ciera.runtime.api.exceptions;

public class ProgramError extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public ProgramError() {
        super();
    }

    public ProgramError(String message, Throwable cause) {
        super(message, cause);
    }

    public ProgramError(String message) {
        super(message);
    }

    public ProgramError(Throwable cause) {
        super(cause);
    }

}
