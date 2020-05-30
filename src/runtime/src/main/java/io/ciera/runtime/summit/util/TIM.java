package io.ciera.runtime.summit.util;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.types.Date;

public interface TIM {

    /**
     * TODO panda
     */
    public long advance_time(final int microseconds) throws XtumlException;

    /**
     * Create a new {@link io.ciera.runtime.summit.types.Date Date} object from
     * the input parameters.
     *
     * @param year the year A.D.
     * @param month the month of the year
     * @param day the day of the month
     * @param hour the hour of the day
     * @param minute the minute of the hour
     * @param second the second of the minute
     * @return a new {@link io.ciera.runtime.summit.types.Date Date} object from
     * the input parameters
     */
    public Date create_date(final int year, final int month, final int day, final int hour, final int minute, final int second) throws XtumlException;

    /**
     * Get the current system time in microseconds since the epoch.
     *
     * @return the current time
     */
    public long current_clock() throws XtumlException;

    /**
     * Get the current date.
     *
     * @return the current date
     */
    public Date current_date() throws XtumlException;

    /**
     * Get the current time in seconds since the epoch.
     *
     * @return the current seconds
     */
    public int current_seconds() throws XtumlException;

    /**
     * Get the day of the month from a date object.
     *
     * @param date the input date
     * @return the day of the month for the input date
     */
    public int get_day(final Date date);

    /**
     * Get the month of the year from a date object.
     *
     * @param date the input date
     * @return the month of the year for the input date
     */
    public int get_month(final Date date);

    /**
     * Get year from a date object.
     *
     * @param date the input date
     * @return the year for the input date
     */
    public int get_year(final Date date);

    /**
     * Get hour of the day from a date object.
     *
     * @param date the input date
     * @return the hour of the day for the input date
     */
    public int get_hour(final Date date);

    /**
     * Get minute of the hour from a date object.
     *
     * @param date the input date
     * @return the minute of the hour for the input date
     */
    public int get_minute(final Date date);

    /**
     * Get second of the minute from a date object.
     *
     * @param date the input date
     * @return the second of the minute for the input date
     */
    public int get_second(final Date date);

    /**
     * TODO panda
     */
    public void set_epoch(final int day, final int month, final int year);

    /**
     * TODO panda
     */
    public long set_time(final int year, final int month, final int day, final int hour, final int minute, final int second, final int microsecond);

    /**
     * TODO panda
     */
    public long time_of_day(final long timeval);

    /**
     * Add time to a running timer.
     *
     * @param microseconds the time to add in microseconds
     * @param timer_inst_ref the timer handle to add time to
     * @return {@literal true} if successful, {@literal false} otherwise
     */
    public boolean timer_add_time(final int microseconds, final TimerHandle timer_inst_ref);

    /**
     * Cancel a running timer.
     *
     * @param timer_inst_ref the timer handle to cancel
     * @return {@literal true} if successful, {@literal false} otherwise
     */
    public boolean timer_cancel(final TimerHandle timer_inst_ref);

    /**
     * Get the remaining time of a running timer in microseconds.
     *
     * @param timer_inst_ref the timer handle to inspect
     * @return the remaining time of the timer in microseconds
     */
    public int timer_remaining_time(final TimerHandle timer_inst_ref);

    /**
     * Reset the timer and set a new duration from the current system time.
     *
     * @param microseconds the new duration from the current system time to set
     * in microseconds
     * @param timer_inst_ref the timer handle to reset
     * @return {@literal true} if successful, {@literal false} otherwise
     */
    public boolean timer_reset_time(final int microseconds, final TimerHandle timer_inst_ref);

    /**
     * Start a new timer to wake in {@code microseconds} microseconds from the
     * current system time. The architecture does not guarantee the timer will
     * wake in exactly {@code microseconds} microseconds, however it does
     * guarantee that it will be at least that long. When the timer wakes, the
     * {@code event_inst} will be added to the system event queue.
     *
     * @param event_inst the event to generate when the timer wakes
     * @param microseconds the duration from the current system time to set in
     * microseconds
     * @return a handle to the newly created timer
     */
    public TimerHandle timer_start(final EventHandle event_inst, final int microseconds);

    /**
     * Start a new recurring timer to wake in {@code microseconds} microseconds
     * from the current system time. The architecture does not guarantee the
     * timer will wake in exactly {@code microseconds} microseconds, however it
     * does guarantee that it will be at least that long. When the timer wakes,
     * the {@code event_inst} will be added to the system event queue. The timer
     * will then be reset to wake again at {@code microseconds} after the system
     * time when the timer event was last generated.
     *
     * @param event_inst the event to generate when the timer wakes
     * @param microseconds the duration from the current system time to set in
     * microseconds
     * @return a handle to the newly created timer
     */
    public TimerHandle timer_start_recurring(final EventHandle event_inst, final int microseconds);

    /**
     * TODO panda
     */
    public String timestamp_format(final long ts, final String format);

    /**
     * TODO panda
     */
    public String timestamp_to_string(final long timestamp);

}
