package io.ciera.runtime.summit2.types;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.function.Function;

import io.ciera.runtime.summit2.application.SystemClock;
import io.ciera.runtime.summit2.exceptions.DeserializationException;

public class Date extends TimeStamp {

    /**
     * Constant representing microseconds per day used for conversion from string.
     */
    private static long MICROS_PER_DAY = 86400000000l;

    /**
     * Use ISO-8601 date/time format.
     */
    private static DateTimeFormatter FORMAT = DateTimeFormatter.ISO_INSTANT;

    /**
     * The calendar instance is initialized with the time stamp given interpreted as
     * microseconds since the Unix epoch. The time zone is UTC.
     */
    private Calendar cal;

    public Date() {
        this(0l, Instant.EPOCH);
    }

    public Date(long timestamp) {
        this(timestamp, Instant.EPOCH);
    }

    public Date(BaseLong o) {
        this(o.getValue());
    }

    public Date(long timestamp, Instant epoch) {
        super(timestamp);
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis((timestamp - epoch.until(Instant.EPOCH, ChronoUnit.MICROS)) / 1000l);
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

    @Override
    public Date add(Object o) {
        return super.add(o).castTo(Date.class);
    }

    @Override
    public Date subtract(Object o) {
        return super.subtract(o).castTo(Date.class);
    }

    @Override
    public Date multiply(Object o) {
        throw new UnsupportedOperationException(
                "Multiplication is not supported for types 'Date' and '" + o != null ? o.getClass().getName()
                        : "null" + "'");
    }

    @Override
    public Date divide(Object o) {
        throw new UnsupportedOperationException(
                "Division is not supported for types 'Date' and '" + o != null ? o.getClass().getName() : "null" + "'");
    }

    @Override
    public Date modulo(Object o) {
        throw new UnsupportedOperationException(
                "Modulo is not supported for types 'Date' and '" + o != null ? o.getClass().getName() : "null" + "'");
    }

    @Override
    public Date remainder(Object o) {
        throw new UnsupportedOperationException(
                "Remainder is not supported for types 'Date' and '" + o != null ? o.getClass().getName()
                        : "null" + "'");
    }

    @Override
    public Date power(Object o) {
        throw new UnsupportedOperationException(
                "Power is not supported for types 'Date' and '" + o != null ? o.getClass().getName() : "null" + "'");
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (Date.class.isAssignableFrom(sourceType)) {
            return o -> (Date) o;
        }

        // Direct conversion from superclass
        if (TimeStamp.class.equals(sourceType)) {
            return o -> new Date((TimeStamp) o);
        }

        // Search in superclass for indirect conversion
        Function<T, ModelType> f = TimeStamp.getCastFunction(sourceType);
        if (f != null) {
            return o -> getCastFunction(TimeStamp.class).apply((TimeStamp) f.apply(o));
        }

        // Didn't find any applicable cast functions
        return null;
    }

    /**
     * Override the 'toString' method to produce an ISO-8601 compliant date/time
     * string.
     */
    @Override
    public String toString() {
        return FORMAT.format(cal.toInstant());
    }

    /**
     * Parse an ISO-8601 date/time string.
     * 
     * @param s The input string
     * @return an instance of Date representative of the input string.
     */
    public static Date fromString(String s) {
        try {
            TemporalAccessor t = FORMAT.parse(s);
            return new Date((t.getLong(ChronoField.INSTANT_SECONDS) * 1000000l)
                    + (t.getLong(ChronoField.NANO_OF_SECOND) / 1000l));
        } catch (NullPointerException | DateTimeParseException e) {
            throw new DeserializationException("Could not parse date", e);
        }

    }

}
