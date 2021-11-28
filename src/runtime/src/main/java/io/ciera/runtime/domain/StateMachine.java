package io.ciera.runtime.domain;

import io.ciera.runtime.action.ActionHome;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.exceptions.CannotHappenException;
import io.ciera.runtime.exceptions.TransitionException;

public abstract class StateMachine implements ActionHome, EventTarget {

    private final String name;
    private final Domain domain;
    private final Logger logger;
    private ExecutionContext context;

    private Enum<?> currentState;

    public StateMachine(String name, Domain domain, Enum<?> initialState) {
        this.name = name;
        this.domain = domain;
        this.logger = domain.getLogger();
        this.context = null;
        this.currentState = initialState;
    }

    public String getName() {
        return name;
    }

    public Enum<?> getCurrentState() {
        return currentState;
    }

    public abstract TransitionRule getTransition(Enum<?> currentState, Event event);

    @Override
    public void consumeEvent(Event event) {
        TransitionRule transition = getTransition(currentState, event);
        traceTxn("TXN START:", getName(), currentState.name(), event.getName(), "...", Logger.ANSI_RESET);
        try {
            Enum<?> newState = transition.execute();
            if (!newState.equals(currentState)) {
                traceTxn("TXN END:", getName(), currentState.name(), event.getName(), newState.name(),
                        Logger.ANSI_GREEN);
                currentState = newState;
            }
        } catch (RuntimeException e) {
            if (!(e instanceof TransitionException)) {
                // Inject transition context information
                throw new TransitionException(currentState, event,
                        "Exception executing transition or state entry action", e);
            } else {
                throw e;
            }
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
    public Logger getLogger() {
        return logger;
    }

    public TransitionRule cannotHappen(Enum<?> currentState, Event event) {
        return () -> {
            traceTxn("TXN END:", getName(), currentState.name(), event.getName(), "CANNOT HAPPEN", Logger.ANSI_RED);
            throw new CannotHappenException(currentState, event, "Event cannot happen.");
        };
    }

    public TransitionRule ignore(Enum<?> currentState, Event event) {
        return () -> {
            traceTxn("TXN END:", getName(), currentState.name(), event.getName(), "IGNORE", Logger.ANSI_YELLOW);
            return currentState;
        };
    }

    private void traceTxn(String txnType, String targetName, String currentState, String eventName, String nextState,
            String nextStateColor) {
        logger.trace("%-15s %-25s: %-50s %-50s => %-40s", txnType, targetName,
                Logger.ANSI_CYAN + currentState + Logger.ANSI_RESET, "[ " + eventName + " ]",
                nextStateColor + nextState + Logger.ANSI_RESET);

    }

}
