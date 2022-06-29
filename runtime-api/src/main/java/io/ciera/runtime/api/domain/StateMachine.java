package io.ciera.runtime.api.domain;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.exceptions.CannotHappenException;

public interface StateMachine extends EventTarget {

  Enum<?> getCurrentState();

  TransitionRule getTransition(Enum<?> currentState, Event event);

  default TransitionRule cannotHappen(final Enum<?> currentState, final Event event) {
    return () -> {
      throw new CannotHappenException();
    };
  }

  default TransitionRule ignore(final Enum<?> currentState, final Event event) {
    return () -> null;
  }
}
