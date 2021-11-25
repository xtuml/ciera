package io.ciera.runtime.domain;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.UniqueId;

public abstract class DynamicObjectInstance extends ObjectInstance {

    private StateMachine stateMachine;

    public DynamicObjectInstance(Domain domain, ExecutionContext context) {
        super(domain, context);
        this.stateMachine = null;
    }

    public DynamicObjectInstance(Domain domain) {
        super(domain);
        this.stateMachine = null;
    }

    public DynamicObjectInstance(UniqueId instanceId, Domain domain, ExecutionContext context) {
        super(instanceId, domain, context);
        this.stateMachine = null;
    }
    
    public void setStateMachine(StateMachine stateMachine) {
        if (this.stateMachine == null) {
            this.stateMachine = stateMachine;
        }
    }

    public Enum<?> getCurrentState() {
        if (stateMachine != null) {
            return stateMachine.getCurrentState();
        } else {
            throw new InstancePopulationException("Dynamic instance has no state machine");
        }
    }

    @Override
    public void consumeEvent(Event event) {
        if (stateMachine != null) {
            stateMachine.consumeEvent(event);
        } else {
            throw new InstancePopulationException("Dynamic instance has no state machine");
        }
    }

}
