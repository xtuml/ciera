package io.ciera.runtime.api.types;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.TimeZone;

import io.ciera.runtime.api.exceptions.DeserializationException;
import io.ciera.runtime.api.time.SystemClock;

public class Date extends TimeStamp {

    public static final Date ZERO = new Date();

    private static final DateTimeFormatter SERIALIZE_FORMAT = DateTimeFormatter.ISO_INSTANT;

    private static final DateTimeFormatter[] PARSE_FORMATS = new DateTimeFormatter[] { DateTimeFormatter.ISO_INSTANT,
            DateTimeFormatter.ISO_DATE_TIME };

    /**
     * The calendar instance is initialized with the time stamp given interpreted as
     * nanoseconds since the Unix epoch. The time zone is UTC.
     */
    private final Calendar cal;

    public Date() {
        this(0l, Instant.EPOCH);
    }

    public Date(long timestamp) {
        this(timestamp, Instant.EPOCH);
    }

    public Date(TimeStamp o) {
        this(o.getValue());
    }

    public Date(long timestamp, Instant epoch) {
        super(timestamp);
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis((timestamp / 1000000l) - epoch.until(Instant.EPOCH, ChronoUnit.MILLIS));
    }

    /**
     * Get the year AD of this Date.
     * 
     * @return the year number.
     */
    public int getYear() {
        return cal.get(Calendar.YEAR);
    }

    /**
     * Get the month of this Date. Values are 1-indexed meaning January=1,
     * February=2, etc.
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
     * Create a new instance of Date from the current time and epoch set in the
     * system clock.
     * 
     * @param clock The clock to reference.
     * @return A constructed Date instance.
     */
    public static Date now(SystemClock clock) {
        return new Date(clock.getTime(), clock.getEpoch());
    }

    /**
     * Override the 'toString' method to produce an ISO-8601 compliant date/time
     * string.
     */
    @Override
    public String toString() {
        return SERIALIZE_FORMAT.format(cal.toInstant());
    }

    /**
     * Parse an ISO-8601 date/time string.
     * 
     * @param s The input string
     * @return an instance of Date representative of the input string.
     */
    public static Date fromString(String s) {
        RuntimeException err = null;
        for (DateTimeFormatter format : PARSE_FORMATS) {
            try {
                TemporalAccessor t = format.parse(s);
                return new Date(
                        (t.getLong(ChronoField.INSTANT_SECONDS) * 1000000000l) + t.getLong(ChronoField.NANO_OF_SECOND));
            } catch (NullPointerException | DateTimeParseException e) {
                err = new DeserializationException("Could not parse date", e);
            }
        }
        throw err;
    }

}
