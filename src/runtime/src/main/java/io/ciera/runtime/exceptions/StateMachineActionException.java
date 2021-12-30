package io.ciera.runtime.exceptions;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.domain.StateMachine;

public class StateMachineActionException extends EventTargetException {

    private static final long serialVersionUID = 1L;
    
    private final StateMachine stateMachine;
    private final Enum<?> currentState;
    
    public StateMachineActionException(String message, Throwable cause, StateMachine stateMachine, Enum<?> currentState, EventTarget target, Event receivedEvent) {
        super(message, cause, target, receivedEvent);
        this.stateMachine = stateMachine;
        this.currentState = currentState;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public Enum<?> getCurrentState() {
        return currentState;
    }

}
