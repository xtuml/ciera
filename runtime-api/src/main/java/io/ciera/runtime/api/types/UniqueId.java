package io.ciera.runtime.api.types;

import java.io.Serializable;
import java.util.UUID;

import io.ciera.runtime.api.exceptions.DeserializationException;

/**
 * The UniqueId class represents a universally unique identifier. It is implemented internally by
 * {@UUID}.
 */
public class UniqueId implements Comparable<UniqueId>, Serializable {

  private static final long serialVersionUID = 1L;

  /** Default value */
  public static final UniqueId ZERO = new UniqueId();

  private static long lastId = 0L;

  private final UUID id;

  public UniqueId() {
    id = new UUID(0, 0);
  }

  public UniqueId(final UUID id) {
    this.id = id;
  }

  public UniqueId(final long id) {
    this.id = new UUID(0, id);
  }

  public UniqueId(final UniqueId id) {
    this.id = id.id;
  }

  public static UniqueId random() {
    if (System.getProperty("io.ciera.runtime.useDeterministicIDs") != null) {
      return new UniqueId(++lastId);
    } else {
      return new UniqueId(UUID.randomUUID());
    }
  }

  @Override
  public int compareTo(final UniqueId o) {
    return id.compareTo(o.id);
  }

  @Override
  public boolean equals(final Object o) {
    return o instanceof UniqueId && id.equals(((UniqueId) o).id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    if (System.getProperty("io.ciera.runtime.useDeterministicIDs") != null) {
      return Long.toString(id.getLeastSignificantBits());
    } else {
      return id.toString();
    }
  }

  public static UniqueId fromString(final String s) {
    try {
      return new UniqueId(UUID.fromString(s));
    } catch (final IllegalArgumentException e) {
      throw new DeserializationException("Could not parse UniqueId", e);
    }
  }
}
