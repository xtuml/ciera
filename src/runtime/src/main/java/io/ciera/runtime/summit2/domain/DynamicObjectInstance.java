package io.ciera.runtime.summit2.domain;

import io.ciera.runtime.summit2.types.UniqueId;
import io.ciera.runtime.summit2.application.Event;
import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Logger;

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
