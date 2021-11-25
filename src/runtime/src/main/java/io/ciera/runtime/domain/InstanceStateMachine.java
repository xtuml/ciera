package io.ciera.runtime.domain;

import io.ciera.runtime.action.InstanceActionHome;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.types.UniqueId;

public abstract class InstanceStateMachine extends StateMachine implements InstanceActionHome {

    private final ObjectInstance self;

    public InstanceStateMachine(String name, Domain domain, Enum<?> initialState, ObjectInstance self) {
        super(name, domain, initialState);
        this.self = self;
    }

    public InstanceStateMachine(String name, Domain domain, ExecutionContext context, Enum<?> initialState,
            ObjectInstance self) {
        super(name, domain, context, initialState);
        this.self = self;
    }

    @Override
    public ObjectInstance self() {
        return self;
    }
    
    @Override
    public UniqueId getTargetHandle() {
        return self.getInstanceId();
    }

}
