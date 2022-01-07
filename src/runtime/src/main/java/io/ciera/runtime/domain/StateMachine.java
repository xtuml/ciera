package io.ciera.runtime.domain;

import io.ciera.runtime.action.ActionHome;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.exceptions.CannotHappenException;
import io.ciera.runtime.exceptions.StateMachineActionException;

public abstract class StateMachine implements ActionHome, EventTarget {

    private final String name;
    private final Domain domain;
    private ExecutionContext context;

    protected Enum<?> currentState;

    public StateMachine(String name, Domain domain, Enum<?> initialState) {
        this.name = name;
        this.domain = domain;
        this.context = null;
        this.currentState = initialState;
    }

    public Enum<?> getCurrentState() {
        return currentState;
    }

    public abstract TransitionRule getTransition(Enum<?> currentState, Event event);

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

    public TransitionRule cannotHappen(Enum<?> currentState, Event event) {
        return () -> {
            traceTxn("TXN END:", name, currentState.name(), event.toString(), "CANNOT HAPPEN", Logger.ANSI_RED);
            throw new CannotHappenException();
        };
    }

    public TransitionRule ignore(Enum<?> currentState, Event event) {
        return () -> {
            traceTxn("TXN END:", name, currentState.name(), event.toString(), "IGNORE", Logger.ANSI_YELLOW);
            return null;
        };
    }

    private void traceTxn(String txnType, String targetName, String currentState, String eventName, String nextState,
            String nextStateColor) {
        getApplication().getLogger().trace("%-15s %-35s: %-50s %-50s => %-40s", txnType, targetName,
                Logger.ANSI_CYAN + currentState + Logger.ANSI_RESET, "[ " + eventName + " ]",
                nextStateColor + nextState + Logger.ANSI_RESET);

    }

    @Override
    public String toString() {
        return name;
    }

}
