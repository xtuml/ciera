package io.ciera.runtime;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.EventTarget;
import io.ciera.runtime.api.ObjectInstance;
import io.ciera.runtime.api.Timer;
import io.ciera.runtime.api.exceptions.DeletedInstanceException;

public abstract class AbstractObjectInstance implements ObjectInstance {

  private static final long serialVersionUID = 1L;

  // TODO dependencies
  private final Supplier<UUID> idAssigner = null;
  private final Domain domain = null;

  private final UUID instanceId;
  private boolean active = true;

  public AbstractObjectInstance() {
    this.instanceId = idAssigner.get();
  }

  public AbstractObjectInstance(final UUID instanceId) {
    this.instanceId = instanceId;
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

  protected boolean isActive() {
    return active;
  }

  @Override
  public Domain getDomain() {
    return domain;
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
  public Domain getDomain(String name) {
    return domain.getDomain(name);
  }

  @Override
  public <E extends Event> void generate(
      final Function<Object[], E> eventBuilder, final EventTarget target, final Object... data) {
    domain.generate(eventBuilder, target, data);
  }

  @Override
  public Timer schedule(final Runnable action, final Duration delay) {
    return domain.schedule(action, delay);
  }

  @Override
  public Timer schedule(final Runnable action, final Duration delay, final Duration period) {
    return domain.schedule(action, delay, period);
  }

  @Override
  public Timer schedule(final Runnable action, final Instant expiration) {
    return domain.schedule(action, expiration);
  }

  @Override
  public Timer schedule(final Runnable action, final Instant expiration, final Duration period) {
    return domain.schedule(action, expiration, period);
  }

  @Override
  public void consumeEvent(final Event event) {
    // TODO
  }
}
