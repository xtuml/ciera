package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;

public class TimerExpiration extends GeneratedEvent {

  private static final long serialVersionUID = 1L;

  public TimerExpiration(final Event event, final EventTarget target) {
    super(event, target);
  }
}