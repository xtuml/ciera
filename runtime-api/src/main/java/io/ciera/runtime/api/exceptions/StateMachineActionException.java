package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.domain.StateMachine;

public class StateMachineActionException extends EventTargetException {

  private static final long serialVersionUID = 1L;

  private final StateMachine stateMachine;
  private final Enum<?> currentState;

  public StateMachineActionException(
      final String message,
      final Throwable cause,
      final StateMachine stateMachine,
      final Enum<?> currentState,
      final EventTarget target,
      final Event receivedEvent) {
    super(message, cause, target, receivedEvent);
    this.stateMachine = stateMachine;
    this.currentState = currentState;
  }

  public StateMachine getStateMachine() {
    return stateMachine;
  }

  public Enum<?> getCurrentState() {
    return currentState;
  }

  @Override
  public String getMessage() {
    return super.getOriginalMessage()
        + ": [stateMachine="
        + getStateMachine()
        + ", currentState="
        + getCurrentState()
        + ", target="
        + getTarget()
        + ", receivedEvent="
        + getReceivedEvent()
        + "]";
  }
}
