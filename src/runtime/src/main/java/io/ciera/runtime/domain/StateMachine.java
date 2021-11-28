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
    private final ExecutionContext context;
    private final Logger logger;

    private Enum<?> currentState;

    public StateMachine(String name, Domain domain, Enum<?> initialState) {
        this(name, domain, null, initialState);
    }

    public StateMachine(String name, Domain domain, ExecutionContext context, Enum<?> initialState) {
        this.name = name;
        this.domain = domain;
        this.context = context;
        this.logger = domain.getLogger();
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
        logger.trace("%-10s %s: %s [%s] => ...", "TXN START:", getName(),
                Logger.ANSI_CYAN + currentState.name() + Logger.ANSI_RESET, event.getName());
        Enum<?> previousState = currentState;
        try {
            Enum<?> newState = transition.execute();
            if (!newState.equals(currentState)) {
                logger.trace("%-10s %s: %s [%s] => %s", "TXN END:", getName(),
                        Logger.ANSI_CYAN + previousState.name() + Logger.ANSI_RESET, event.getName(),
                        Logger.ANSI_GREEN + currentState.name() + Logger.ANSI_RESET);
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
    public Logger getLogger() {
        return logger;
    }

    public TransitionRule cannotHappen(Enum<?> currentState, Event event) {
        return () -> {
            logger.trace("%-10s %s: %s [%s] => %s", "TXN CH:", getName(),
                    Logger.ANSI_CYAN + currentState.name() + Logger.ANSI_RESET, event.getName(),
                    Logger.ANSI_RED + "CANNOT HAPPEN" + Logger.ANSI_RESET);
            throw new CannotHappenException(currentState, event, "Event cannot happen.");
        };
    }

    public TransitionRule ignore(Enum<?> currentState, Event event) {
        return () -> {
            logger.trace("%-10s %s: %s [%s] => %s", "TXN IGN:", getName(),
                    Logger.ANSI_CYAN + currentState.name() + Logger.ANSI_RESET, event.getName(),
                    Logger.ANSI_YELLOW + "IGNORED" + Logger.ANSI_RESET);
            return currentState;
        };
    }

}
