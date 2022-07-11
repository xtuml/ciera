package io.ciera.runtime;

import java.util.UUID;

import io.ciera.runtime.api.DynamicObjectInstance;
import io.ciera.runtime.api.Event;

public abstract class AbstractDynamicObjectInstance extends AbstractObjectInstance
    implements DynamicObjectInstance {

  private static final long serialVersionUID = 1L;

  private Enum<?> currentState;

  public AbstractDynamicObjectInstance() {}

  public AbstractDynamicObjectInstance(final UUID instanceId) {
    super(instanceId);
  }

  @Override
  public Enum<?> getCurrentState() {
    return currentState;
  }

  @Override
  public void consumeEvent(final Event event) {
    // TODO
  }
}
