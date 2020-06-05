package io.ciera.runtime.summit.types;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;

import io.ciera.runtime.summit.application.IRunContext;

public class Date implements IXtumlType, Comparable<Date> {

    private long timestamp;  // microseconds
    private Calendar cal;

    public Date() {
    	this(0L);
    }

    public Date(long timestamp) {
        this.timestamp = timestamp;
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(timestamp / 1000L);
    }

    public int getYear() {
        return cal.get(Calendar.YEAR);
    }

    public int getMonth() {
        return cal.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return cal.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return cal.get(Calendar.SECOND);
    }

    public static Date now(IRunContext runContext) {
        long unixMicros = runContext.time() - runContext.getEpoch().until(Instant.EPOCH, ChronoUnit.MICROS);
        return new Date(unixMicros);
    }

    @Override
    public int compareTo(Date o) {
        return timestamp == o.timestamp ? 0 : timestamp < o.timestamp ? -1 : 1;
    }

    public boolean lessThan(Date o) {
        return compareTo(o) < 0;
    }

    public boolean lessThanOrEqual(Date o) {
        return compareTo(o) <= 0;
    }

    public boolean greaterThan(Date o) {
        return compareTo(o) > 0;
    }

    public boolean greaterThanOrEqual(Date o) {
        return compareTo(o) >= 0;
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
        return Duration.ofMillis(timestamp).toString();
    }

}
