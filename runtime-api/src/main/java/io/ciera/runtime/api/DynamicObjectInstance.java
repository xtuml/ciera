package io.ciera.runtime.api;

public interface DynamicObjectInstance extends ObjectInstance, StateMachine {

  void initialize(final Domain domain, final Enum<?> initialState);
}
