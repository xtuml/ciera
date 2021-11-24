package io.ciera.runtime.summit2.exceptions;

import io.ciera.runtime.summit2.application.Event;

public class TransitionException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private Enum<?> state;
    private Event event;

    public TransitionException(Enum<?> state, Event event) {
        super();
        this.state = state;
        this.event = event;
    }

    public TransitionException(Enum<?> state, Event event, String message) {
        super(message);
        this.state = state;
        this.event = event;
    }

    public TransitionException(Enum<?> state, Event event, String message, Throwable cause) {
        super(message, cause);
        this.state = state;
        this.event = event;
    }

    public TransitionException(Enum<?> state, Event event, String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.state = state;
        this.event = event;
    }

    public TransitionException(Enum<?> state, Event event, Throwable cause) {
        super(cause);
        this.state = state;
        this.event = event;
    }

    public Enum<?> getState() {
        return state;
    }

    public Event getEvent() {
        return event;
    }

}
