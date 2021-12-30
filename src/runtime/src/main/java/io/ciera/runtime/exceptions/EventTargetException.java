package io.ciera.runtime.exceptions;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;

public class EventTargetException extends RuntimeException {

    private static final long serialVersionUID = 1l;
    
    private final EventTarget target;
    private final Event receivedEvent;

    public EventTargetException(String message, EventTarget target, Event receivedEvent) {
        super(message);
        this.target = target;
        this.receivedEvent = receivedEvent;
    }
    
    public EventTargetException(String message, Throwable cause, EventTarget target, Event receivedEvent) {
        super(message, cause);
        this.target = target;
        this.receivedEvent = receivedEvent;
    }
    
    public EventTarget getTarget() {
        return target;
    }
    
    public Event getReceivedEvent() {
        return receivedEvent;
    }
    
}
