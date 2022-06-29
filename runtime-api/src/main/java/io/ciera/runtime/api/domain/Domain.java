package io.ciera.runtime.api.domain;

import java.util.function.Function;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;
import io.ciera.runtime.api.types.UniqueId;

/**
 * A domain is a composite of translated model elements including classes, relationships, types,
 * functions, etc. The component provides access to out-bound (required) interface messages and the
 * instance population for every action within it.
 */
public interface Domain extends InstancePopulation, EventTarget, ActionHome {

  String getName();

  void initialize();

  EventTarget getEventTarget(UniqueId targetId);

  MessageTarget getMessageTarget(Class<? extends MessageTarget> targetClass);

  Port getPort(String portName);

  @Override
  default Domain getDomain() {
    return this;
  }

  @Override
  default UniqueId getTargetId() {
    return null;
  }

  default <E extends Event> void generateEvent(
      final Function<Object[], E> eventBuilder, final EventTarget target, final Object... data) {
    getContext().generateEvent(eventBuilder, target, data);
  }

  default <E extends Event> void generateEventToSelf(
      final Function<Object[], E> eventBuilder, final EventTarget target, final Object... data) {
    getContext().generateEventToSelf(eventBuilder, target, data);
  }

  default <E extends Event> Timer scheduleEvent(
      final Function<Object[], E> eventBuilder,
      final EventTarget target,
      final Duration delay,
      final Object... eventData) {
    return getContext().scheduleEvent(eventBuilder, target, delay, eventData);
  }

  default <E extends Event> Timer scheduleEvent(
      final Function<Object[], E> eventBuilder,
      final EventTarget target,
      final TimeStamp expiration,
      final Object... eventData) {
    return getContext().scheduleEvent(eventBuilder, target, expiration, eventData);
  }

  default <E extends Event> Timer scheduleRecurringEvent(
      final Function<Object[], E> eventBuilder,
      final EventTarget target,
      final Duration delay,
      final Duration period,
      final Object... eventData) {
    return getContext().scheduleRecurringEvent(eventBuilder, target, delay, period, eventData);
  }

  default <E extends Event> Timer scheduleRecurringEvent(
      final Function<Object[], E> eventBuilder,
      final EventTarget target,
      final TimeStamp expiration,
      final Duration period,
      final Object... eventData) {
    return getContext().scheduleRecurringEvent(eventBuilder, target, expiration, period, eventData);
  }

  default void halt() {
    getContext().halt();
  }

  @Deprecated
  default void delay(final Duration delay) {
    getContext().delay(delay);
  }
}
