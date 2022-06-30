package io.ciera.runtime.api.domain;

import java.util.function.Supplier;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.exceptions.CannotHappenException;

public interface StateMachine extends EventTarget {

  Enum<?> getCurrentState();

  Supplier<Enum<?>> getTransition(Enum<?> currentState, Event event);

  default Supplier<Enum<?>> cannotHappen(final Enum<?> currentState, final Event event) {
    return () -> {
      throw new CannotHappenException();
    };
  }

  default Supplier<Enum<?>> ignore(final Enum<?> currentState, final Event event) {
    return () -> null;
  }
}
