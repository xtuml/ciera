package io.ciera.runtime.summit2.types;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.function.Function;

import io.ciera.runtime.summit2.exceptions.DeserializationException;

public class Date extends ModelType implements Numeric, Comparable<Object> {

    /**
     * TODO Microseconds since the epoch
     */
    private long timestamp;

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

    public Date(long timestamp, Instant epoch) {
        this.timestamp = timestamp;
        long unixMicros = timestamp - epoch.until(Instant.EPOCH, ChronoUnit.MICROS);
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(unixMicros / 1000L);
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

    @SuppressWarnings("unchecked")
    public static <R extends Object> Function<ModelType, R> getCastFunctionForType(Class<R> type,
            Class<?> ignoreClass) {
        // Direct cast to superclass
        if (Numeric.class.equals(type)) {
            return o -> (R) new BaseNumeric(((Date) o).timestamp, 0d);
        }

        // Attempt to find cast function in superclass
        if (!Numeric.class.equals(ignoreClass)) {
            Function<ModelType, R> f = BaseNumeric.getCastFunctionForType(type, Date.class);
            if (f != null) {
                return o -> f.apply((ModelType) ((Date) o).castTo(Numeric.class));
            }
        }

        // Didn't find any applicable cast functions
        return null;
    }

    @Override
    public int compareTo(Object o) {
        Date d = ModelType.castTo(Date.class, o);
        return timestamp == d.timestamp ? 0 : timestamp < d.timestamp ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Date && timestamp == ((Date) o).timestamp;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(timestamp);
    }

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
    public Date add(Object n) {
        // TODO this my lose precision
        return new Date(timestamp + ModelType.castTo(Date.class, n).timestamp);
    }

    @Override
    public Date subtract(Object n) {
        // TODO this my lose precision
        return new Date(timestamp - ModelType.castTo(Date.class, n).timestamp);
    }

    @Override
    public Date multiply(Object n) {
        throw new UnsupportedOperationException(
                "Multiplication is not supported for types 'Date' and '" + n != null ? n.getClass().getName()
                        : "null" + "'");
    }

    @Override
    public Date divide(Object n) {
        throw new UnsupportedOperationException(
                "Division is not supported for types 'Date' and '" + n != null ? n.getClass().getName() : "null" + "'");
    }

    @Override
    public Date modulo(Object n) {
        throw new UnsupportedOperationException(
                "Modulo is not supported for types 'Date' and '" + n != null ? n.getClass().getName() : "null" + "'");
    }

    @Override
    public Date remainder(Object n) {
        throw new UnsupportedOperationException(
                "Remainder is not supported for types 'Date' and '" + n != null ? n.getClass().getName()
                        : "null" + "'");
    }

    @Override
    public Date power(Object n) {
        throw new UnsupportedOperationException(
                "Power is not supported for types 'Date' and '" + n != null ? n.getClass().getName() : "null" + "'");
    }

    @Override
    public <R> Function<ModelType, R> getCastFunction(Class<R> type) {
        return Date.getCastFunctionForType(type, null);
    }

}
