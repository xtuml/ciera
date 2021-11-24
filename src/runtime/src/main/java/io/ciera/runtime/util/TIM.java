package io.ciera.runtime.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.application.SystemClock;
import io.ciera.runtime.application.Timer;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Utility;
import io.ciera.runtime.types.Date;
import io.ciera.runtime.types.Duration;
import io.ciera.runtime.types.EventHandle;
import io.ciera.runtime.types.ModelType;
import io.ciera.runtime.types.TimeStamp;
import io.ciera.runtime.types.TimerHandle;

public class TIM extends Utility {

    private SystemClock clock;

    public TIM(Domain domain, ExecutionContext context, Logger logger) {
        super(domain, context, logger);
        this.clock = context.getClock();
    }

    public TimeStamp advance_time(final long microseconds) {
        clock.setTime(clock.getTime() + microseconds);
        return new TimeStamp(clock.getTime());
    }

    public Date create_date(final int day, final int hour, final int minute, final int month, final int second,
            final int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, minute, second);
        return new Date(cal);
    }

    public TimeStamp current_clock() {
        return new TimeStamp(clock.getTime());
    }

    public Date current_date() {
        return Date.now(clock);
    }

    public int current_seconds() {
        return ModelType.castTo(Integer.class, current_clock().divide(1000000000l));
    }

    public int get_day(final Date date) {
        return date.getDay();
    }

    public int get_month(final Date date) {
        return date.getMonth();
    }

    public int get_year(final Date date) {
        return date.getYear();
    }

    public int get_hour(final Date date) {
        return date.getHour();
    }

    public int get_minute(final Date date) {
        return date.getMinute();
    }

    public int get_second(final Date date) {
        return date.getSecond();
    }

    public void set_epoch(final int day, final int month, final int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        clock.setEpoch(Instant.ofEpochMilli(cal.getTimeInMillis()));
    }

    public TimeStamp set_time(final int year, final int month, final int day, final int hour, final int minute,
            final int second, final int microsecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(year, month - 1, day, hour, minute, second);
        cal.set(Calendar.MILLISECOND, 0);
        long unixMicros = (cal.getTimeInMillis() * 1000L) + microsecond;
        long systemMicros = unixMicros - Instant.EPOCH.until(clock.getEpoch(), ChronoUnit.MICROS);
        clock.setTime(systemMicros * 1000l);
        return new TimeStamp(clock.getTime());
    }

    public TimeStamp time_of_day(final long timeval) {
        long unixNanos = clock.getTime() - clock.getEpoch().until(Instant.EPOCH, ChronoUnit.NANOS);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new TimeStamp(unixNanos - (cal.getTimeInMillis() * 1000000L));
    }

    public boolean timer_add_time(final int microseconds, final TimerHandle timer_inst_ref) {
        Timer timer = getDomain().getTimer(timer_inst_ref);
        if (timer != null) {
            Event event = getDomain().getEvent(timer.getEventHandle());
            EventTarget target = getDomain().getTarget(timer.getTargetHandle());
            boolean cancelled = clock.cancelTimer(timer_inst_ref);
            if (cancelled) {
                timer.setExpiration(timer.getExpiration() + (microseconds * 1000l));
                getContext().scheduleEvent(event, target, timer);
                return true;
            }
        }
        return false;
    }

    public boolean timer_cancel(final TimerHandle timer_inst_ref) {
        return clock.cancelTimer(timer_inst_ref);
    }

    public int timer_remaining_time(final TimerHandle timer_inst_ref) {
        Timer timer = getDomain().getTimer(timer_inst_ref);
        if (timer != null) {
            return (int) (timer.getExpiration() - clock.getTime());
        }
        return 0;
    }

    public boolean timer_reset_time(final int microseconds, final TimerHandle timer_inst_ref) {
        Timer timer = getDomain().getTimer(timer_inst_ref);
        if (timer != null) {
            Event event = getDomain().getEvent(timer.getEventHandle());
            EventTarget target = getDomain().getTarget(timer.getTargetHandle());
            boolean cancelled = clock.cancelTimer(timer_inst_ref);
            if (cancelled) {
                timer.setExpiration(clock.getTime() + (microseconds * 1000l));
                getContext().scheduleEvent(event, target, timer);
                return true;
            }
        }
        return false;
    }

    public TimerHandle timer_start(final EventHandle event_inst, final int microseconds) {
        Event event = getDomain().getEvent(event_inst);
        if (event != null) {
            EventTarget target = getDomain().getTarget(event.getTargetHandle());
            return getContext().scheduleEvent(event, target, new Duration(microseconds * 1000l));
        }
        return null;
    }

    public TimerHandle timer_start_recurring(final EventHandle event_inst, final int microseconds) {
        Event event = getDomain().getEvent(event_inst);
        if (event != null) {
            EventTarget target = getDomain().getTarget(event.getTargetHandle());
            return getContext().scheduleEvent(event, target, new Duration(microseconds * 1000l),
                    new Duration(microseconds * 1000l));
        }
        return null;
    }

    public String timestamp_format(final TimeStamp timestamp, final String format) {
        long unixNanos = timestamp.getValue() - clock.getEpoch().until(Instant.EPOCH, ChronoUnit.NANOS);
        return LocalDateTime.ofEpochSecond(unixNanos / 1000000000L, (int) (unixNanos % 1000000000L), ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern(format));
    }

    public String timestamp_to_string(final TimeStamp timestamp) {
        return timestamp.toString();
    }

}
