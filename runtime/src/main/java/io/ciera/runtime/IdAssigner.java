package io.ciera.runtime;

import java.util.UUID;
import java.util.function.Supplier;

public final class IdAssigner {

  public static final UUID NULL_ID = new UUID(0L, 0L);

  public static final Supplier<UUID> RANDOM = () -> UUID.randomUUID();

  private static volatile long currentId = 1;

  public static final Supplier<UUID> SEQUENTIAL =
      () -> {
        synchronized (IdAssigner.class) {
          return new UUID(0L, currentId++);
        }
      };

  private IdAssigner() {}
}
