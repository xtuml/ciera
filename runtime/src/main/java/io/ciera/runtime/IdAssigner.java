package io.ciera.runtime;

import java.util.PrimitiveIterator.OfLong;
import java.util.UUID;
import java.util.stream.LongStream;

public final class IdAssigner {

  public static final UUID NULL_ID = new UUID(0, 0);

  private static final OfLong counter = LongStream.iterate(0l, i -> i + 1).iterator();

  public static UUID random() {
    return UUID.randomUUID();
  }

  public static synchronized UUID incremental() {
    return new UUID(0l, counter.nextLong());
  }
}
