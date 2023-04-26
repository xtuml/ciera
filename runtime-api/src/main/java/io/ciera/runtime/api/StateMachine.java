package io.ciera.runtime.api;

import java.util.function.Supplier;

import io.ciera.runtime.api.exceptions.CannotHappenException;

public interface StateMachine extends EventTarget, TaskSupplier {

  Enum<?> getCurrentState();

  Supplier<Enum<?>> getTransition(Enum<?> currentState, Event event);

  static Supplier<Enum<?>> cannotHappen(
      final StateMachine inst, final Enum<?> currentState, final Event event) {
    return () -> {
      throw new CannotHappenException(inst, currentState, event);
    };
  }

  static Supplier<Enum<?>> ignore(final Enum<?> currentState, final Event event) {
    return null;
  }
}
