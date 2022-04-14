package io.ciera.runtime.api.domain;

public interface DynamicObjectInstance extends ObjectInstance {

  public Enum<?> getCurrentState();

  public void setCurrentState(Enum<?> newState);
}
