package io.ciera.runtime.domain;

import io.ciera.runtime.api.action.InstanceActionHome;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.DynamicObjectInstance;
import io.ciera.runtime.api.exceptions.InstanceStateMachineActionException;
import io.ciera.runtime.api.types.UniqueId;

public abstract class InstanceStateMachine extends AbstractStateMachine implements InstanceActionHome {

    private final DynamicObjectInstance self;

    public InstanceStateMachine(Domain domain, DynamicObjectInstance self) {
        super(String.format("%s [ISM]", self), domain);
        this.self = self;
    }

    @Override
    public void consumeEvent(Event event) {
        try {
            executeTransition(event, self()::setCurrentState);
        } catch (RuntimeException e) {
            throw new InstanceStateMachineActionException("Exception in state machine action", e, this,
                    getCurrentState(), self(), event);
        }
    }

    @Override
    public Enum<?> getCurrentState() {
        return self().getCurrentState();
    }

    @Override
    public DynamicObjectInstance self() {
        return self;
    }

    @Override
    public String toString() {
        return String.format("%s [ISM]", self());
    }

    @Override
    public UniqueId getTargetId() {
        return self.getInstanceId();
    }

}
