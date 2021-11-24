package io.ciera.runtime.summit2.exceptions;

import io.ciera.runtime.summit2.application.Event;

public class CannotHappenException extends TransitionException {

    private static final long serialVersionUID = 1l;

    public CannotHappenException(Enum<?> state, Event event, String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(state, event, message, cause, enableSuppression, writableStackTrace);
    }

    public CannotHappenException(Enum<?> state, Event event, String message, Throwable cause) {
        super(state, event, message, cause);
    }

    public CannotHappenException(Enum<?> state, Event event, String message) {
        super(state, event, message);
    }

    public CannotHappenException(Enum<?> state, Event event, Throwable cause) {
        super(state, event, cause);
    }

    public CannotHappenException(Enum<?> state, Event event) {
        super(state, event);
    }

}
