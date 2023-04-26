package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.StateMachine;

public class CannotHappenException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CannotHappenException(
      final StateMachine inst, final Enum<?> currentState, final Event event) {
    super(
        String.format(
            "Event '%s' cannot happen in state '%s': %s",
            event.getName(), currentState, inst.getClass().getSimpleName()));
  }
}
