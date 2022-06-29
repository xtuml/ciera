package io.ciera.runtime.api.types;

import java.io.Serializable;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalUnit;

import io.ciera.runtime.api.exceptions.DeserializationException;

/**
 * The Duration class represents a period of time. It is represented as a quantity of nanoseconds.
 * Durations can be represented as ISO-8601 strings.
 */
public class Duration implements Serializable, Comparable<Duration> {

  private static final long serialVersionUID = 1L;

  /** Default value */
  public static final Duration ZERO = new Duration();

  private final java.time.Duration value;

  public Duration() {
    this(0L);
  }

  public Duration(final long value) {
    this(java.time.Duration.ofNanos(value));
  }

  public Duration(final long value, final TemporalUnit unit) {
    this(java.time.Duration.of(value, unit));
  }

  public Duration(final Duration o) {
    this(o.value);
  }

  public Duration(final java.time.Duration value) {
    this.value = value;
  }

  public long getValue() {
    return value.toNanos();
  }

  public int getSeconds() {
    return (int) value.getSeconds();
  }

  /** Override the 'toString' method to produce an ISO-8601 compliant duration string. */
  @Override
  public String toString() {
    // Create ISO 8601 compliant duration string
    return value.toString();
  }

  /**
   * Parse an ISO-8601 duration string.
   *
   * @param s The input string
   * @return an instance of Duration representative of the input string.
   */
  public static Duration fromString(final String s) {
    // Parse ISO 8601 compliant duration string
    try {
      return new Duration(java.time.Duration.parse(s));
    } catch (NullPointerException | DateTimeParseException e) {
      throw new DeserializationException("Could not parse duration", e);
    }
  }

  @Override
  public int compareTo(final Duration o) {
    return value.compareTo(o.value);
  }

  // Arithmetic operations
  public TimeStamp plus(final TimeStamp t) {
    return new TimeStamp(value.toNanos() + t.getValue());
  }

  public Duration plus(final Duration d) {
    return new Duration(value.toNanos() + d.getValue());
  }

  public Duration minus(final Duration d) {
    return new Duration(value.toNanos() - d.getValue());
  }

  public Duration times(final Number n) {
    return new Duration(value.toNanos() * n.longValue());
  }

  public Duration dividedBy(final Number n) {
    return new Duration(value.toNanos() / n.longValue());
  }

  // TODO mod, remainder, others?
}
