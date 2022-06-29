package io.ciera.runtime.api.types;

import java.io.Serializable;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.TimeZone;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.exceptions.DeserializationException;
import io.ciera.runtime.api.time.SystemClock;

/**
 * The TimeStamp class represents a point in time. It is represented as a quantity of nanoseconds
 * elapsed since an epoch (reference point in time). TimeStamp instances do not inherently define
 * the epoch they are referenced from, but will be interpreted by the runtime based on the current
 * settings of the {@link SystemClock}.
 */
public class TimeStamp implements Serializable, Comparable<TimeStamp> {

  private static final long serialVersionUID = 1L;

  private static final DateTimeFormatter SERIALIZE_FORMAT = DateTimeFormatter.ISO_INSTANT;

  private static final DateTimeFormatter[] PARSE_FORMATS =
      new DateTimeFormatter[] {DateTimeFormatter.ISO_INSTANT, DateTimeFormatter.ISO_DATE_TIME};

  /** Default value */
  public static final TimeStamp ZERO = new TimeStamp();

  private final long nanosecondsValue;

  /**
   * The calendar instance is initialized with the time stamp given interpreted as nanoseconds since
   * the Unix epoch. The time zone is UTC.
   */
  private final Calendar cal;

  public TimeStamp() {
    this(0L);
  }

  public TimeStamp(final long nanosecondsValue) {
    this(nanosecondsValue, Instant.EPOCH);
  }

  public TimeStamp(final long nanosecondsValue, final Instant epoch) {
    this.nanosecondsValue = nanosecondsValue;
    cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone("UTC"));
    cal.setTimeInMillis(
        nanosecondsValue / 1000000L - epoch.until(Instant.EPOCH, ChronoUnit.MILLIS));
  }

  public TimeStamp(final TimeStamp o) {
    this(o.getValue());
  }

  public long getValue() {
    return nanosecondsValue;
  }

  /**
   * Get the year AD of this TimeStamp.
   *
   * @return the year number.
   */
  public int getYear() {
    return cal.get(Calendar.YEAR);
  }

  /**
   * Get the month of this TimeStamp. Values are 1-indexed meaning January=1, February=2, etc.
   *
   * @return the month number.
   */
  public int getMonth() {
    return cal.get(Calendar.MONTH) + 1;
  }

  /**
   * Get the day of the month of this date.
   *
   * @return the day number.
   */
  public int getDay() {
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Get the hour of the day of this date.
   *
   * @return the hour number.
   */
  public int getHour() {
    return cal.get(Calendar.HOUR_OF_DAY);
  }

  /**
   * Get the minute of the hour of this date.
   *
   * @return the minute number.
   */
  public int getMinute() {
    return cal.get(Calendar.MINUTE);
  }

  /**
   * Get the second of the minute of this date.
   *
   * @return the second number.
   */
  public int getSecond() {
    return cal.get(Calendar.SECOND);
  }

  /**
   * Create a new instance of TimeStamp from the current time and epoch set in the system clock.
   *
   * @param clock The clock to reference.
   * @return A constructed TimeStamp instance.
   */
  public static TimeStamp now(final SystemClock clock) {
    return new TimeStamp(clock.getTime());
  }

  public static TimeStamp now() {
    return now(Application.getInstance().getClock());
  }

  /**
   * Parse an ISO-8601 date/time string.
   *
   * @param s The input string
   * @return an instance of TimeStamp representative of the input string.
   */
  public static TimeStamp fromString(final String s) {
    try {
      return new TimeStamp(Long.parseLong(s));
    } catch (final NumberFormatException e) {
      RuntimeException err = null;
      for (final DateTimeFormatter format : PARSE_FORMATS) {
        try {
          final TemporalAccessor t = format.parse(s);
          return new TimeStamp(
              t.getLong(ChronoField.INSTANT_SECONDS) * 1000000000L
                  + t.getLong(ChronoField.NANO_OF_SECOND));
        } catch (NullPointerException | DateTimeParseException e2) {
          err = new DeserializationException("Could not parse timestamp", e2);
        }
      }
      throw err;
    }
  }

  /** Override the 'toString' method to produce an ISO-8601 compliant date/time string. */
  @Override
  public String toString() {
    return SERIALIZE_FORMAT.format(cal.toInstant());
  }

  @Override
  public int compareTo(final TimeStamp o) {
    return Long.compare(nanosecondsValue, o.nanosecondsValue);
  }

  // Arithmetic operations
  public TimeStamp plus(final Duration d) {
    return new TimeStamp(nanosecondsValue + d.getValue());
  }

  public TimeStamp minus(final Duration d) {
    return new TimeStamp(nanosecondsValue - d.getValue());
  }

  public Duration minus(final TimeStamp t) {
    return new Duration(nanosecondsValue - t.getValue());
  }
}
