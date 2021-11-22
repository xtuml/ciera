package io.ciera.runtime.summit2.types;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.function.Function;

import io.ciera.runtime.summit2.exceptions.DeserializationException;

public class Date extends TimeStamp {

    /**
     * TODO calendar instance used to get date fields
     */
    private Calendar cal;

    public Date() {
        this(0L, Instant.EPOCH);
    }

    public Date(long timestamp) {
        this(timestamp, Instant.EPOCH);
    }

    public Date(BaseLong o) {
        this(o.getValue());
    }

    public Date(long timestamp, Instant epoch) {
        super(timestamp);
        long unixMicros = timestamp - epoch.until(Instant.EPOCH, ChronoUnit.MICROS);
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(unixMicros / 1000L);
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
     * TODO
     * 
     * @return
     */
    public int getYear() {
        return cal.get(Calendar.YEAR);
    }

    /**
     * TODO
     * 
     * @return
     */
    public int getMonth() {
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * TODO
     * 
     * @return
     */
    public int getDay() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * TODO
     * 
     * @return
     */
    public int getHour() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * TODO
     * 
     * @return
     */
    public int getMinute() {
        return cal.get(Calendar.MINUTE);
    }

    /**
     * TODO
     * 
     * @return
     */
    public int getSecond() {
        return cal.get(Calendar.SECOND);
    }

    /**
     * TODO TODO test
     * 
     * @return
     */
    /*
     * public static Date now(SystemClock clock) { return new Date(clock.time(),
     * clock.getEpoch()); }
     */

    @Override
    public String toString() {
        // Create ISO 8601 compliant date string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(cal.getTime());
    }

    public static Date fromString(String s) {
        try {
            // TODO parse iso string
            return new Date(Long.parseLong(s) * 1000L);
        } catch (NullPointerException | NumberFormatException e) {
            throw new DeserializationException("Cannot deserialize 'Date' from input: " + s, e);
        }
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

}
