package io.ciera.runtime.domain;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.StateMachine;
import io.ciera.runtime.api.domain.TransitionRule;
import io.ciera.runtime.api.exceptions.StateMachineActionException;

public abstract class AbstractStateMachine implements StateMachine {

    private final String name;
    private final Domain domain;
    private ExecutionContext context;

    protected Enum<?> currentState;

    public AbstractStateMachine(String name, Domain domain, Enum<?> initialState) {
        this.name = name;
        this.domain = domain;
        this.context = null;
        this.currentState = initialState;
    }

    @Override
    public Enum<?> getCurrentState() {
        return currentState;
    }

    protected void executeTransition(Event event) {
        TransitionRule transition = getTransition(currentState, event);
        traceTxn("TXN START:", name, currentState.name(), event.toString(), "...", Logger.ANSI_RESET);
        Enum<?> newState = transition.execute();
        if (newState != null) {
            traceTxn("TXN END:", name, currentState.name(), event.toString(), newState.name(), Logger.ANSI_GREEN);
            currentState = newState;
        }
    }

    @Override
    public void consumeEvent(Event event) {
        try {
            executeTransition(event);
        } catch (RuntimeException e) {
            throw new StateMachineActionException("Exception in state machine action", e, this, currentState, this,
                    event);
        }
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return context != null ? context : getDomain().getContext();
    }

    @Override
    public void attachTo(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return name;
    }

}
