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
        logger.trace("TXN START: %s: %s [%s] => ...", getName(), currentState.name(), event.getName());
        Enum<?> previousState = currentState;
        try {
            currentState = transition.execute();
            logger.trace("TXN END: %s: %s [%s] => %s", getName(), previousState.name(), event.getName(),
                    currentState.name());
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
            logger.trace("TXN: %s: %s [%s] => %s", getName(), currentState.name(), event.getName(), "CANNOT HAPPEN");
            throw new CannotHappenException(currentState, event, "Event cannot happen.");
        };
    }

    public TransitionRule ignore(Enum<?> currentState, Event event) {
        return () -> {
            logger.trace("TXN: %s: %s [%s] => %s", getName(), currentState.name(), event.getName(), "IGNORED");
            return currentState;
        };
    }

}
