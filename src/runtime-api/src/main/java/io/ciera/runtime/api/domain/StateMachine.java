package io.ciera.runtime.api.domain;

import io.ciera.runtime.api.action.ActionHome;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.api.exceptions.CannotHappenException;

public interface StateMachine extends ActionHome, EventTarget {

    public Enum<?> getCurrentState();

    public TransitionRule getTransition(Enum<?> currentState, Event event);

    public default TransitionRule cannotHappen(Enum<?> currentState, Event event) {
        return () -> {
            traceTxn("TXN END:", this.toString(), currentState.name(), event.toString(), "CANNOT HAPPEN",
                    Logger.ANSI_RED);
            throw new CannotHappenException();
        };
    }

    public default TransitionRule ignore(Enum<?> currentState, Event event) {
        return () -> {
            traceTxn("TXN END:", this.toString(), currentState.name(), event.toString(), "IGNORE", Logger.ANSI_YELLOW);
            return null;
        };
    }

    public default void traceTxn(String txnType, String targetName, String currentState, String eventName,
            String nextState, String nextStateColor) {
        getApplication().getLogger().trace("%-15s %-35s: %-50s %-50s => %-40s", txnType, targetName,
                Logger.ANSI_CYAN + currentState + Logger.ANSI_RESET, "[ " + eventName + " ]",
                nextStateColor + nextState + Logger.ANSI_RESET);

    }

}
