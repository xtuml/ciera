package io.ciera.runtime.domain;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.StateMachine;
import io.ciera.runtime.api.types.UniqueId;

public abstract class AbstractStateMachine implements StateMachine {

  private final String name;
  private final Domain domain;

  public AbstractStateMachine(final String name, final Domain domain) {
    this.name = name;
    this.domain = domain;
  }

  protected void executeTransition(final Event event, final Consumer<Enum<?>> updateCurrentState) {
    final Supplier<Enum<?>> transition = getTransition(getCurrentState(), event);
    traceTxn(
        "TXN START:", name, getCurrentState().name(), event.toString(), "...", Logger.ANSI_RESET);
    final Enum<?> newState = transition.get();
    if (newState != null) {
      traceTxn(
          "TXN END:",
          name,
          getCurrentState().name(),
          event.toString(),
          newState.name(),
          Logger.ANSI_GREEN);
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

  private void traceTxn(
      final String txnType,
      final String targetName,
      final String currentState,
      final String eventName,
      final String nextState,
      final String nextStateColor) {
    getContext()
        .getApplication()
        .getLogger()
        .trace(
            "%-15s %-35s: %-50s %-50s => %-40s",
            txnType,
            targetName,
            Logger.ANSI_CYAN + currentState + Logger.ANSI_RESET,
            "[ " + eventName + " ]",
            nextStateColor + nextState + Logger.ANSI_RESET);
  }
}
