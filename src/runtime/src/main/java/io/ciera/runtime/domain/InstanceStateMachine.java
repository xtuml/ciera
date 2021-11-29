package io.ciera.runtime.domain;

import io.ciera.runtime.action.InstanceActionHome;

public abstract class InstanceStateMachine extends StateMachine implements InstanceActionHome {

    private final ObjectInstance self;

    public InstanceStateMachine(Domain domain, Enum<?> initialState, ObjectInstance self) {
        super(String.format("%s [ISM]", self.getName()), domain, initialState);
        this.self = self;
    }

    @Override
    public ObjectInstance self() {
        return self;
    }

    @Override
    public String getName() {
        return String.format("%s [ISM]", self().getName());
    }

    @Override
    public String toString() {
        return getName();
    }

}
