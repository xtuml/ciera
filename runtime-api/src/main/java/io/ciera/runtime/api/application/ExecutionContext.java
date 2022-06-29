package io.ciera.runtime.api.application;

import java.util.concurrent.Executor;
import java.util.function.Function;

import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;

public interface ExecutionContext extends Executor {

  String getName();

  Application getApplication();

  <E extends Event> void generateEvent(
      Function<Object[], E> eventBuilder, EventTarget target, Object... data);

  <E extends Event> void generateEventToSelf(
      Function<Object[], E> eventBuilder, EventTarget target, Object... data);

  <E extends Event> Timer scheduleEvent(
      Function<Object[], E> eventBuilder, EventTarget target, Duration delay, Object... eventData);

  <E extends Event> Timer scheduleEvent(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      TimeStamp expiration,
      Object... eventData);

  Timer scheduleAction(Duration delay, Runnable action);

  Timer scheduleAction(TimeStamp expiration, Runnable action);

  <E extends Event> Timer scheduleRecurringEvent(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      Duration delay,
      Duration period,
      Object... eventData);

  <E extends Event> Timer scheduleRecurringEvent(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      TimeStamp expiration,
      Duration period,
      Object... eventData);

  Timer scheduleRecurringAction(Duration delay, Duration period, Runnable action);

  Timer scheduleRecurringAction(TimeStamp expiration, Duration period, Runnable action);

  void halt();

  @Deprecated
  void delay(Duration delay);

  ExecutionMode getExecutionMode();

  ModelIntegrityMode getModelIntegrityMode();

  SystemClock getClock();

  public enum ExecutionMode {
    INTERLEAVED,
    SEQUENTIAL;
  }

  public enum ModelIntegrityMode {
    STRICT,
    RELAXED,
    OFF;
  }
}
