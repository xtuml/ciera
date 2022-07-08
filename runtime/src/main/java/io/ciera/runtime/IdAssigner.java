package io.ciera.runtime;

import java.util.UUID;

public final class IdAssigner {

  public static final UUID NULL_ID = new UUID(0L, 0L);

  public static UUID random() {
    return UUID.randomUUID();
  }
}
