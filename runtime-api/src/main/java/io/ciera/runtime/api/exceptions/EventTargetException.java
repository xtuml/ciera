package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;

public class EventTargetException extends RuntimeException {

  private static final long serialVersionUID = 1l;

  private final EventTarget target;
  private final Event receivedEvent;

  public EventTargetException(String message, EventTarget target, Event receivedEvent) {
    this(message, null, target, receivedEvent);
  }

  public EventTargetException(
      String message, Throwable cause, EventTarget target, Event receivedEvent) {
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

  public String getOriginalMessage() {
    return super.getMessage();
  }

  @Override
  public String getMessage() {
    return super.getMessage() + ": [target=" + target + ", receivedEvent=" + receivedEvent + "]";
  }
}
