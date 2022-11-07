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

  protected final Supplier<UUID> idAssigner = IdAssigner::incremental;
  private Domain domain;

  private final UUID instanceId;
  private boolean active;

  public AbstractObjectInstance() {
    this.instanceId = idAssigner.get();
    this.active = false;
  }

  public AbstractObjectInstance(final UUID instanceId) {
    this.instanceId = instanceId;
    this.active = false;
  }

  @Override
  public void initialize(final Domain domain) {
    this.domain = domain;
    this.active = true;
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

  @Override
  public Object getIdentifier() {
    return getInstanceId();
  }

  protected boolean isActive() {
    return active;
  }

  protected void setDomain(final Domain domain) {
    this.domain = domain;
  }

  @Override
  public Domain getDomain() {
    return domain;
  }

  @Override
  public String toString() {
    return String.format(
        "%s[%.8s]", getClass().getSimpleName(), instanceId.getLeastSignificantBits());
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

  public <E extends Event> Timer schedule(
      Function<Object[], E> eventBuilder, EventTarget target, Duration delay, Object... data) {
    return domain.schedule(eventBuilder, target, delay, data);
  }

  public <E extends Event> Timer schedule(
      Function<Object[], E> eventBuilder, EventTarget target, Instant expiration, Object... data) {
    return domain.schedule(eventBuilder, target, expiration, data);
  }

  public <E extends Event> Timer scheduleRecurring(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      Duration delay,
      Duration period,
      Object... data) {
    return domain.schedule(eventBuilder, target, delay, period, data);
  }

  public <E extends Event> Timer scheduleRecurring(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      Instant expiration,
      Duration period,
      Object... data) {
    return domain.schedule(eventBuilder, target, expiration, period, data);
  }

  @Override
  public void consumeEvent(final Event event) {
    // TODO
  }
}
