package io.ciera.runtime.util;

import java.time.Instant;

import io.ciera.runtime.api.Architecture;
import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.SystemClock;
import io.ciera.runtime.api.Timer;

public class TIM {

  // TODO implement TIM methods

  private final Architecture arch = Architecture.getInstance();
  private final SystemClock clock = arch.getClock();

  public TIM(Object domain) {}

  public Instant advance_time(final long microseconds) {
    throw new UnsupportedOperationException();
  }

  public Instant create_date(
      final int day,
      final int hour,
      final int minute,
      final int month,
      final int second,
      final int year) {
    throw new UnsupportedOperationException();
  }

  public Instant current_clock() {
    return clock.get();
  }

  public Instant current_date() {
    return clock.get();
  }

  public int current_seconds() {
    throw new UnsupportedOperationException();
  }

  public int get_day(final Instant date) {
    throw new UnsupportedOperationException();
  }

  public int get_month(final Instant date) {
    throw new UnsupportedOperationException();
  }

  public int get_year(final Instant date) {
    throw new UnsupportedOperationException();
  }

  public int get_hour(final Instant date) {
    throw new UnsupportedOperationException();
  }

  public int get_minute(final Instant date) {
    throw new UnsupportedOperationException();
  }

  public int get_second(final Instant date) {
    throw new UnsupportedOperationException();
  }

  public void set_epoch(final int day, final int month, final int year) {
    throw new UnsupportedOperationException();
  }

  public Instant set_time(
      final int year,
      final int month,
      final int day,
      final int hour,
      final int minute,
      final int second,
      final int microsecond) {
    throw new UnsupportedOperationException();
  }

  public Instant time_of_day(final long timeval) {
    throw new UnsupportedOperationException();
  }

  @Deprecated(since = "3.0.0")
  public boolean timer_add_time(final int microseconds, final Timer timer_inst_ref) {
    throw new UnsupportedOperationException(
        "'TIM::timer_add_time' is deprecated. User 'Timer.addTime'");
  }

  @Deprecated(since = "3.0.0")
  public boolean timer_cancel(final Timer timer_inst_ref) {
    throw new UnsupportedOperationException("'TIM::timer_cancel' is deprecated. Use 'Timer.cancel");
  }

  @Deprecated(since = "3.0.0")
  public int timer_remaining_time(final Timer timer_inst_ref) {
    throw new UnsupportedOperationException(
        "'TIM::timer_remaining_time' is deprecated. Use 'Timer.remainingTime'");
  }

  @Deprecated(since = "3.0.0")
  public boolean timer_reset_time(final int microseconds, final Timer timer_inst_ref) {
    throw new UnsupportedOperationException(
        "'TIM::timer_reset_time' is deprecated. User 'Timer.reset'");
  }

  @Deprecated(since = "3.0.0")
  public Timer timer_start(final Event event_inst, final int microseconds) {
    throw new UnsupportedOperationException(
        "'TIM::timer_start' is deprecated. Use 'ExecutionContext.scheduleEvent'");
  }

  @Deprecated(since = "3.0.0")
  public Timer timer_start_recurring(final Event event_inst, final int microseconds) {
    throw new UnsupportedOperationException(
        "'TIM::timer_start_recurring' is deprecated. Use 'ExecutionContext.scheduleEvent'");
  }

  public String timestamp_format(final Instant timestamp, final String format) {
    throw new UnsupportedOperationException();
  }

  public String timestamp_to_string(final Instant timestamp) {
    return timestamp.toString();
  }
}
