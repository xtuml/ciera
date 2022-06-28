package io.ciera.runtime.api.action;

import java.util.function.Function;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;

/**
 * An action home represents an element that can contain generated action language statements. The
 * action home provides a standard way to access the domain (for the purpose of accessing domain
 * resources such as relators, terminator messages, utilities, etc). It also gives access to the
 * execution context for time and event generation.
 */
public interface ActionHome {

  /**
   * Get the parent domain.
   *
   * @return {@link Domain} the parent domain within which the actions are defined.
   */
  public Domain getDomain();

  public default Domain getDomain(String domainName) {
    return getApplication().getDomain(domainName);
  }

  /**
   * Get the execution context.
   *
   * @return {@link ExecutionContext} the execution context within which the actions run.
   */
  public ExecutionContext getContext();

  public default Application getApplication() {
    return getContext().getApplication();
  }

  public default <E extends Event> void generateEvent(
      Function<Object[], E> eventBuilder, EventTarget target, Object... data) {
    getContext().generateEvent(eventBuilder, target, data);
  }

  public default <E extends Event> void generateEventToSelf(
      Function<Object[], E> eventBuilder, EventTarget target, Object... data) {
    getContext().generateEventToSelf(eventBuilder, target, data);
  }

  public default <E extends Event> Timer scheduleEvent(
      Function<Object[], E> eventBuilder, EventTarget target, Duration delay, Object... eventData) {
    return getContext().scheduleEvent(eventBuilder, target, delay, eventData);
  }

  public default <E extends Event> Timer scheduleEvent(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      TimeStamp expiration,
      Object... eventData) {
    return getContext().scheduleEvent(eventBuilder, target, expiration, eventData);
  }

  public default Timer scheduleAction(Duration delay, Runnable action) {
    return getContext().scheduleAction(delay, action);
  }

  public default Timer scheduleAction(TimeStamp expiration, Runnable action) {
    return getContext().scheduleAction(expiration, action);
  }

  public default <E extends Event> Timer scheduleRecurringEvent(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      Duration delay,
      Duration period,
      Object... eventData) {
    return getContext().scheduleRecurringEvent(eventBuilder, target, delay, period, eventData);
  }

  public default <E extends Event> Timer scheduleRecurringEvent(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      TimeStamp expiration,
      Duration period,
      Object... eventData) {
    return getContext().scheduleRecurringEvent(eventBuilder, target, expiration, period, eventData);
  }

  public default Timer scheduleRecurringAction(Duration delay, Duration period, Runnable action) {
    return getContext().scheduleRecurringAction(delay, period, action);
  }

  public default Timer scheduleRecurringAction(
      TimeStamp expiration, Duration period, Runnable action) {
    return getContext().scheduleRecurringAction(expiration, period, action);
  }

  public default void halt() {
    getContext().halt();
  }

  @Deprecated
  public default void delay(Duration delay) {
    getContext().delay(delay);
  }
}
