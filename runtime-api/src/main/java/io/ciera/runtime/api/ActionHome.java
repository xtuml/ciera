package io.ciera.runtime.api;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ActionHome {

  void generate(Function<Object[], Event> eventBuilder, EventTarget target, Object... data);

  void generateAccelerated(
      Function<Object[], Event> eventBuilder, EventTarget target, Object... data);

  Timer schedule(
      Function<Object[], Event> eventBuilder, EventTarget target, Duration delay, Object... data);

  Timer schedule(
      Function<Object[], Event> eventBuilder,
      EventTarget target,
      Instant expiration,
      Object... data);

  Timer scheduleRecurring(
      Function<Object[], Event> eventBuilder,
      EventTarget target,
      Duration delay,
      Duration period,
      Object... data);

  Timer scheduleRecurring(
      Function<Object[], Event> eventBuilder,
      EventTarget target,
      Instant expiration,
      Duration period,
      Object... data);

  Domain getDomain();

  default Logger getLogger() {
    return LoggerFactory.getLogger(getClass());
  }
}
