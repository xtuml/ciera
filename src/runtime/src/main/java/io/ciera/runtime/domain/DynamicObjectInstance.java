package io.ciera.runtime.domain;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.types.UniqueId;

public abstract class DynamicObjectInstance extends ObjectInstance {

    private StateMachine stateMachine;

    public DynamicObjectInstance(Domain domain, ExecutionContext context, Logger logger, StateMachine stateMachine) {
        super(domain, context, logger);
        this.stateMachine = stateMachine;
    }

    public DynamicObjectInstance(UniqueId instanceId, Domain domain, ExecutionContext context, Logger logger,
            StateMachine stateMachine) {
        super(instanceId, domain, context, logger);
        this.stateMachine = stateMachine;
    }

    public Enum<?> getCurrentState() {
        return stateMachine.getCurrentState();
    }

    @Override
    public void consumeEvent(Event event) {
        stateMachine.consumeEvent(event);
    }

}
