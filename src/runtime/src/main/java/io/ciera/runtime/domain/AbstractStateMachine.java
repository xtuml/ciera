package io.ciera.runtime.domain;

import java.util.function.Consumer;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.StateMachine;
import io.ciera.runtime.api.domain.TransitionRule;
import io.ciera.runtime.api.types.UniqueId;

public abstract class AbstractStateMachine implements StateMachine {

    private final String name;
    private final Domain domain;

    public AbstractStateMachine(String name, Domain domain) {
        this.name = name;
        this.domain = domain;
    }

    protected void executeTransition(Event event, Consumer<Enum<?>> updateCurrentState) {
        TransitionRule transition = getTransition(getCurrentState(), event);
        traceTxn("TXN START:", name, getCurrentState().name(), event.toString(), "...", "\u001b[0m");
        Enum<?> newState = transition.execute();
        if (newState != null) {
            traceTxn("TXN END:", name, getCurrentState().name(), event.toString(), newState.name(), "\u001b[32m");
            updateCurrentState.accept(newState);
        }
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return domain.getContext();
    }

    @Override
    public UniqueId getTargetId() {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

}
