package io.ciera.runtime.api;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public interface ActionHome {

  <E extends Event> void generate(
      Function<Object[], E> eventBuilder, EventTarget target, Object... data);

  <E extends Event> Timer schedule(
      Function<Object[], E> eventBuilder, EventTarget target, Duration delay, Object... data);

  <E extends Event> Timer schedule(
      Function<Object[], E> eventBuilder, EventTarget target, Instant expiration, Object... data);

  <E extends Event> Timer scheduleRecurring(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      Duration delay,
      Duration period,
      Object... data);

  <E extends Event> Timer scheduleRecurring(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      Instant expiration,
      Duration period,
      Object... data);

  Domain getDomain();

  Domain getDomain(final String domainName);
}
