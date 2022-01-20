package io.ciera.runtime.domain;

import io.ciera.runtime.api.action.InstanceActionHome;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.DynamicObjectInstance;
import io.ciera.runtime.api.exceptions.InstanceStateMachineActionException;

public abstract class InstanceStateMachine extends AbstractStateMachine implements InstanceActionHome {

    private final DynamicObjectInstance self;

    public InstanceStateMachine(Domain domain, Enum<?> initialState, DynamicObjectInstance self) {
        super(String.format("%s [ISM]", self), domain, initialState);
        this.self = self;
    }

    @Override
    public void consumeEvent(Event event) {
        try {
            executeTransition(event);
        } catch (RuntimeException e) {
            throw new InstanceStateMachineActionException("Exception in state machine action", e, this, currentState,
                    self(), event);
        }
    }

    @Override
    public DynamicObjectInstance self() {
        return self;
    }

    @Override
    public String toString() {
        return String.format("%s [ISM]", self());
    }

}
