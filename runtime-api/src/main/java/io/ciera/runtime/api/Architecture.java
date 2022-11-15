package io.ciera.runtime.api;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

public final class Architecture {

  private static final Architecture INSTANCE = new Architecture();

  public static final UUID NULL_ID = new UUID(0, 0);

  private Supplier<UUID> idAssigner = UUID::randomUUID;
  private SystemClock clock = Instant::now;

  private Architecture() {}

  public Supplier<UUID> getIdAssigner() {
    return idAssigner;
  }

  public SystemClock getClock() {
    return clock;
  }

  public static Architecture getInstance() {
    return INSTANCE;
  }
}
