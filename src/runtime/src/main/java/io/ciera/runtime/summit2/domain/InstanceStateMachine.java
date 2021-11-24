package io.ciera.runtime.summit2.domain;

import io.ciera.runtime.summit2.action.InstanceActionHome;
import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Logger;

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
