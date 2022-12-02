package io.ciera.runtime;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;

import io.ciera.runtime.api.Architecture;
import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.EventTarget;
import io.ciera.runtime.api.ObjectInstance;
import io.ciera.runtime.api.Timer;
import io.ciera.runtime.api.exceptions.DeletedInstanceException;

public abstract class AbstractObjectInstance implements ObjectInstance {

  private static final long serialVersionUID = 1L;

  protected final Architecture arch = Architecture.getInstance();
  private Domain domain;

  private final UUID instanceId;
  private boolean active;

  public AbstractObjectInstance() {
    this.instanceId = arch.getIdAssigner().get();
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
    return String.format("%s[%.8s]", getClass().getSimpleName(), instanceId);
  }

  @Override
  public void generate(
      final Function<Object[], Event> eventBuilder,
      final EventTarget target,
      final Object... data) {
    domain.generate(eventBuilder, target, data);
  }

  @Override
  public void generateAccelerated(
      final Function<Object[], Event> eventBuilder,
      final EventTarget target,
      final Object... data) {
    domain.generateAccelerated(eventBuilder, target, data);
  }

  @Override
  public Timer schedule(
      Function<Object[], Event> eventBuilder, EventTarget target, Duration delay, Object... data) {
    return domain.schedule(eventBuilder, target, delay, data);
  }

  @Override
  public Timer schedule(
      Function<Object[], Event> eventBuilder,
      EventTarget target,
      Instant expiration,
      Object... data) {
    return domain.schedule(eventBuilder, target, expiration, data);
  }

  @Override
  public Timer scheduleRecurring(
      Function<Object[], Event> eventBuilder,
      EventTarget target,
      Duration delay,
      Duration period,
      Object... data) {
    return domain.schedule(eventBuilder, target, delay, period, data);
  }

  @Override
  public Timer scheduleRecurring(
      Function<Object[], Event> eventBuilder,
      EventTarget target,
      Instant expiration,
      Duration period,
      Object... data) {
    return domain.schedule(eventBuilder, target, expiration, period, data);
  }

  @Override
  public void queueEvent(Event event) {} // TODO

  @Override
  public void queueAcceleratedEvent(Event event) {} // TODO

  @Override
  public void queueDelayedEvent(Timer delayedEvent) {} // TODO

  @Override
  public void cancelDelayedEvent(Timer delayedEvent) {} // TODO
}
