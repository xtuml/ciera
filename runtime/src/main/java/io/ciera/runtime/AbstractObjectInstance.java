package io.ciera.runtime;

import java.util.UUID;

import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.ObjectInstance;
import io.ciera.runtime.api.exceptions.DeletedInstanceException;

public abstract class AbstractObjectInstance implements ObjectInstance {

  private static final long serialVersionUID = 1L;

  private final UUID instanceId;
  private boolean active;

  public AbstractObjectInstance() {
    this(IdAssigner.random());
  }

  public AbstractObjectInstance(final UUID instanceId) {
    this.instanceId = instanceId;
    active = true;
  }

  @Override
  public UUID getInstanceId() {
    return instanceId;
  }

  @Override
  public void delete() {
    if (isActive()) {
      active = false;
      getDomain().removeInstance(this);
    } else {
      throw new DeletedInstanceException(
          "Cannot delete instance that has already been deleted", getDomain(), this);
    }
  }

  boolean isActive() {
    return active;
  }

  @Override
  public Object getIdentifier() {
    return InstanceIdentifier.EMPTY_IDENTIFIER;
  }

  @Override
  public Object getIdentifier(final int index) {
    throw new IndexOutOfBoundsException(index);
  }

  @Override
  public String toString() {
    return String.format("%s[%.8s]", getClass().getSimpleName(), instanceId);
  }

  @Override
  public void consumeEvent(final Event event) {
    // TODO
  }
}
