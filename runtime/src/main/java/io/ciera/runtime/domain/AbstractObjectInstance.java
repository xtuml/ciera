package io.ciera.runtime.domain;

import java.util.Set;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.DynamicObjectInstance;
import io.ciera.runtime.api.domain.EmptyInstance;
import io.ciera.runtime.api.domain.ObjectInstance;
import io.ciera.runtime.api.exceptions.DeletedInstanceException;
import io.ciera.runtime.api.exceptions.EventTargetException;
import io.ciera.runtime.api.types.UniqueId;

public abstract class AbstractObjectInstance implements ObjectInstance {

  private static final long serialVersionUID = 1L;

  private final UniqueId instanceId;
  private final String domainName;
  private transient Domain domain;
  private boolean active;

  public AbstractObjectInstance() {
    instanceId = null;
    domainName = null;
    domain = null;
    active = false;
  }

  public AbstractObjectInstance(final Domain domain) {
    this(UniqueId.random(), domain);
  }

  public AbstractObjectInstance(final UniqueId instanceId, final Domain domain) {
    this.instanceId = instanceId;
    domainName = domain.getName();
    this.domain = domain;
    active = true;
  }

  public Set<ObjectInstance> getSubtypeInstances() {
    return Set.of();
  }

  @Override
  public UniqueId getInstanceId() {
    return instanceId;
  }

  @Override
  public void delete() {
    if (isActive()) {
      getDomain().deleteInstance(this);
      active = false;
    } else {
      throw new DeletedInstanceException(
          "Cannot delete instance that has already been deleted", getDomain(), this);
    }
  }

  @Override
  public boolean isEmpty() {
    return this instanceof EmptyInstance;
  }

  @Override
  public boolean isActive() {
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
  public Domain getDomain() {
    if (domain == null) {
      domain = Application.getInstance().getDomain(domainName);
    }
    return domain;
  }

  @Override
  public ExecutionContext getContext() {
    return getDomain().getContext();
  }

  @Override
  public String toString() {
    return String.format("%s[%.8s]", getClass().getSimpleName(), instanceId);
  }

  @Override
  public void consumeEvent(final Event event) {
    if (isDynamic()) {
      getContext()
          .getApplication()
          .getLogger()
          .trace("Passing event through non-dynamic supertype: " + this);
      getSubtypeInstances().stream()
          .filter(AbstractObjectInstance.class::isInstance)
          .map(AbstractObjectInstance.class::cast)
          .filter(AbstractObjectInstance::isDynamic)
          .forEach(o -> o.consumeEvent(event));
    } else {
      throw new EventTargetException("Cannot generate event to non-dynamic instance", this, event);
    }
  }

  protected boolean isDynamic() {
    return this instanceof DynamicObjectInstance
        || getSubtypeInstances().stream()
            .filter(AbstractObjectInstance.class::isInstance)
            .map(AbstractObjectInstance.class::cast)
            .anyMatch(AbstractObjectInstance::isDynamic);
  }
}
