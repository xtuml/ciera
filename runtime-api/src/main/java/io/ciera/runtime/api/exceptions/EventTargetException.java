package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.EventTarget;

public class EventTargetException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final EventTarget target;
  private final Event receivedEvent;

  public EventTargetException(
      final String message, final EventTarget target, final Event receivedEvent) {
    this(message, null, target, receivedEvent);
  }

  public EventTargetException(
      final String message,
      final Throwable cause,
      final EventTarget target,
      final Event receivedEvent) {
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
