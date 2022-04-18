package io.ciera.runtime.domain;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.DynamicObjectInstance;
import io.ciera.runtime.api.domain.StateMachine;
import io.ciera.runtime.api.exceptions.DeletedInstanceException;
import io.ciera.runtime.api.exceptions.EmptyInstanceException;
import io.ciera.runtime.api.exceptions.EventTargetException;
import io.ciera.runtime.api.types.UniqueId;

public abstract class AbstractDynamicObjectInstance extends AbstractObjectInstance
    implements DynamicObjectInstance {

  private static final long serialVersionUID = 1L;

  private Enum<?> currentState;

  public AbstractDynamicObjectInstance() {
    super();
  }

  public AbstractDynamicObjectInstance(Domain domain) {
    super(domain);
  }

  public AbstractDynamicObjectInstance(UniqueId instanceId, Domain domain) {
    super(instanceId, domain);
  }

  public abstract StateMachine getStateMachine();

  @Override
  public Enum<?> getCurrentState() {
    return currentState;
  }

  @Override
  public void setCurrentState(Enum<?> newState) {
    currentState = newState;
  }

  @Override
  public void consumeEvent(Event event) {
    if (isActive()) {
      if (notEmpty()) {
        if (getStateMachine() != null) {
          if (event != null) {
            getStateMachine().consumeEvent(event);
            getSubtypeInstances().stream()
                .filter(AbstractObjectInstance.class::isInstance)
                .map(AbstractObjectInstance.class::cast)
                .filter(AbstractObjectInstance::isDynamic)
                .forEach(o -> o.consumeEvent(event));
          } else {
            throw new EventTargetException("Cannot consume null event", this, event);
          }
        } else {
          throw new IllegalStateException("Dynamic instance has no state machine");
        }
      } else {
        throw new EventTargetException(
            "Failed to deliver event",
            new EmptyInstanceException("Empty instance cannot process event", getDomain(), this),
            this,
            event);
      }
    } else {
      throw new EventTargetException(
          "Failed to deliver event",
          new DeletedInstanceException(
              "Cannot deliver event to deleted instance", getDomain(), this),
          this,
          event);
    }
  }
}
