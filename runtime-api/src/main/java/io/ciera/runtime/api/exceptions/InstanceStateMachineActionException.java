package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.domain.DynamicObjectInstance;
import io.ciera.runtime.api.domain.StateMachine;

public class InstanceStateMachineActionException extends StateMachineActionException {

  private static final long serialVersionUID = 1L;

  public InstanceStateMachineActionException(
      final String message,
      final Throwable cause,
      final StateMachine stateMachine,
      final Enum<?> currentState,
      final DynamicObjectInstance instance,
      final Event receivedEvent) {
    super(message, cause, stateMachine, currentState, instance, receivedEvent);
  }

  public DynamicObjectInstance getInstance() {
    return (DynamicObjectInstance) super.getTarget();
  }

  @Override
  public String getMessage() {
    return super.getOriginalMessage()
        + ": [stateMachine="
        + getStateMachine()
        + ", currentState="
        + getCurrentState()
        + ", instance="
        + getInstance()
        + ", receivedEvent="
        + getReceivedEvent()
        + "]";
  }
}
