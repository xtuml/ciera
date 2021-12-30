package io.ciera.runtime.exceptions;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.domain.DynamicObjectInstance;
import io.ciera.runtime.domain.StateMachine;

public class InstanceStateMachineActionException extends StateMachineActionException {
    
    private static final long serialVersionUID = 1l;
    
    public InstanceStateMachineActionException(String message, Throwable cause, StateMachine stateMachine,
            Enum<?> currentState, DynamicObjectInstance instance, Event receivedEvent) {
        super(message, cause, stateMachine, currentState, instance, receivedEvent);
    }
    
    public DynamicObjectInstance getInstance() {
        return (DynamicObjectInstance) super.getTarget();
    }

}
