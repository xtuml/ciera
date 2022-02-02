package io.ciera.runtime.domain;

import java.util.function.Consumer;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.StateMachine;
import io.ciera.runtime.api.domain.TransitionRule;

public abstract class AbstractStateMachine implements StateMachine {

    private final String name;
    private final Domain domain;
    private ExecutionContext context;

    public AbstractStateMachine(String name, Domain domain) {
        this.name = name;
        this.domain = domain;
        this.context = null;
    }

    protected void executeTransition(Event event, Consumer<Enum<?>> updateCurrentState) {
        TransitionRule transition = getTransition(getCurrentState(), event);
        traceTxn("TXN START:", name, getCurrentState().name(), event.toString(), "...", Logger.ANSI_RESET);
        Enum<?> newState = transition.execute();
        if (newState != null) {
            traceTxn("TXN END:", name, getCurrentState().name(), event.toString(), newState.name(), Logger.ANSI_GREEN);
            updateCurrentState.accept(newState);
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
