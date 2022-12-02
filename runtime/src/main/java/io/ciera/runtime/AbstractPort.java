package io.ciera.runtime;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.EventTarget;
import io.ciera.runtime.api.Port;
import io.ciera.runtime.api.Timer;

public abstract class AbstractPort implements Port {

  private final String name;
  private final Domain domain;

  private Port peer = null;

  public AbstractPort(final String name, final Domain domain) {
    this.name = name;
    this.domain = domain;
  }

  @Override
  public void setPeer(Port peer) {
    this.peer = peer;
  }

  @Override
  public Port getPeer() {
    return peer;
  }

  @Override
  public void generate(Function<Object[], Event> eventBuilder, EventTarget target, Object... data) {
    domain.generate(eventBuilder, target, data);
  }

  @Override
  public void generateAccelerated(
      Function<Object[], Event> eventBuilder, EventTarget target, Object... data) {
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
    return domain.scheduleRecurring(eventBuilder, target, delay, period, data);
  }

  @Override
  public Timer scheduleRecurring(
      Function<Object[], Event> eventBuilder,
      EventTarget target,
      Instant expiration,
      Duration period,
      Object... data) {
    return domain.scheduleRecurring(eventBuilder, target, expiration, period, data);
  }

  @Override
  public Domain getDomain() {
    return domain;
  }

  @Override
  public String toString() {
    return "Port[" + domain.getName() + "::" + name + "]";
  }

  public static void satisfy(Port a, Port b) {
    a.setPeer(b);
    b.setPeer(a);
  }
}
