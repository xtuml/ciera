package io.ciera.runtime.api.domain;

public interface DynamicObjectInstance extends ObjectInstance {

  Enum<?> getCurrentState();

  void setCurrentState(Enum<?> newState);
}
