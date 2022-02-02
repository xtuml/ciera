package io.ciera.runtime.api.domain;

import io.ciera.runtime.api.application.EventTarget;

public interface DynamicObjectInstance extends ObjectInstance, EventTarget {

    public Enum<?> getCurrentState();
    public void setCurrentState(Enum<?> newState);

}
