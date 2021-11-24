package io.ciera.runtime.domain;

import io.ciera.runtime.action.InstanceActionHome;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;

public abstract class InstanceStateMachine extends StateMachine implements InstanceActionHome {

    private ObjectInstance self;

    public InstanceStateMachine(String name, Domain domain, ExecutionContext context, Logger logger,
            Enum<?> initialState, ObjectInstance self) {
        super(name, domain, context, logger, initialState);
    }

    @Override
    public ObjectInstance self() {
        return self;
    }

}
