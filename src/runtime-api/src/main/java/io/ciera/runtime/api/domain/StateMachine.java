package io.ciera.runtime.api.domain;

import io.ciera.runtime.api.action.ActionHome;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.exceptions.CannotHappenException;

public interface StateMachine extends ActionHome, EventTarget {

    public Enum<?> getCurrentState();

    public TransitionRule getTransition(Enum<?> currentState, Event event);

    public default TransitionRule cannotHappen(Enum<?> currentState, Event event) {
        return () -> {
            traceTxn("TXN END:", this.toString(), currentState.name(), event.toString(), "CANNOT HAPPEN", "\u001B[31m");
            throw new CannotHappenException();
        };
    }

    public default TransitionRule ignore(Enum<?> currentState, Event event) {
        return () -> {
            traceTxn("TXN END:", this.toString(), currentState.name(), event.toString(), "IGNORE", "\u001B[33m");
            return null;
        };
    }

    public default void traceTxn(String txnType, String targetName, String currentState, String eventName,
            String nextState, String nextStateColor) {
        getApplication().getLogger().trace("{}", new Object() {
            @Override
            public String toString() {
                return String.format("%-15s %-35s: %-50s %-50s => %-40s", txnType, targetName,
                        "\u001B[36m" + currentState + "\u001B[0m", "[ " + eventName + " ]",
                        nextStateColor + nextState + "\u001B[0m");
            }
        });
    }

}
