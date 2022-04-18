package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.domain.StateMachine;

public class StateMachineActionException extends EventTargetException {

  private static final long serialVersionUID = 1L;

  private final StateMachine stateMachine;
  private final Enum<?> currentState;

  public StateMachineActionException(
      String message,
      Throwable cause,
      StateMachine stateMachine,
      Enum<?> currentState,
      EventTarget target,
      Event receivedEvent) {
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
