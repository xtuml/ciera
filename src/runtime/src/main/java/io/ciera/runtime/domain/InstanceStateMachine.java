package io.ciera.runtime.domain;

import io.ciera.runtime.action.InstanceActionHome;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.exceptions.InstanceStateMachineActionException;

public abstract class InstanceStateMachine extends StateMachine implements InstanceActionHome {

    private final DynamicObjectInstance self;

    public InstanceStateMachine(Domain domain, Enum<?> initialState, DynamicObjectInstance self) {
        super(String.format("%s [ISM]", self.getName()), domain, initialState);
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
    public String getName() {
        return String.format("%s [ISM]", self().toString());
    }

    @Override
    public String toString() {
        return getName();
    }

}
