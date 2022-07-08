package io.ciera.runtime.api;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public interface ActionHome {

  <E extends Event> void generate(
      Function<Object[], E> eventBuilder, EventTarget target, Object... data);

  Domain getDomain();

  Domain getDomain(final String domainName);

  Timer schedule(Runnable action, Duration delay);

  Timer schedule(Runnable action, Duration delay, Duration period);

  Timer schedule(Runnable action, Instant expiration);

  Timer schedule(Runnable action, Instant expiration, Duration period);
}
