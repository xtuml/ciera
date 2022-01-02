package io.ciera.runtime.domain;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.exceptions.DeletedInstanceException;
import io.ciera.runtime.exceptions.EmptyInstanceException;
import io.ciera.runtime.exceptions.EventTargetException;
import io.ciera.runtime.types.UniqueId;

public abstract class DynamicObjectInstance extends ObjectInstance {

    private StateMachine stateMachine;

    public DynamicObjectInstance() {
        super();
    }

    public DynamicObjectInstance(Domain domain) {
        super(domain);
        this.stateMachine = null;
    }

    public DynamicObjectInstance(UniqueId instanceId, Domain domain) {
        super(instanceId, domain);
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
            throw new IllegalStateException("Dynamic instance has no state machine");
        }
    }

    @Override
    public void consumeEvent(Event event) {
        if (isAlive()) {
            if (!isEmpty()) {
                if (stateMachine != null) {
                    if (event != null) {
                        stateMachine.consumeEvent(event);
                    } else {
                        throw new EventTargetException("Cannot consume null event", this, event);
                    }
                } else {
                    throw new IllegalStateException("Dynamic instance has no state machine");
                }
            } else {
                throw new EventTargetException("Failed to deliver event",
                        new EmptyInstanceException("Empty instance cannot process event", getDomain(), this), this,
                        event);
            }
        } else {
            throw new EventTargetException("Failed to deliver event",
                    new DeletedInstanceException("Cannot deliver event to deleted instance", getDomain(), this), this,
                    event);
        }
    }
}
