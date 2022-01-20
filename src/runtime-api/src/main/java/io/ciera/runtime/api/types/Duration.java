package io.ciera.runtime.api.types;

import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import io.ciera.runtime.api.exceptions.DeserializationException;

/**
 * The Duration class represents a period of time. It is represented as a
 * quantity of nanoseconds. Durations can be represented as ISO-8601 strings.
 */
public class Duration {

    /**
     * Default value
     */
    public static final Duration ZERO = new Duration();

    private final long value;

    public Duration() {
        this(0l);
    }

    public Duration(long value) {
        if (value >= 0) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Cannot create a negative delay");
        }
    }

    public Duration(long value, TemporalUnit unit) {
        this(java.time.Duration.of(value, unit).toNanos());
    }

    public Duration(Duration o) {
        this(o.getValue());
    }

    public long getValue() {
        return value;
    }

    /**
     * Override the 'toString' method to produce an ISO-8601 compliant duration
     * string.
     */
    @Override
    public String toString() {
        // Create ISO 8601 compliant duration string
        return java.time.Duration.of(getValue(), ChronoUnit.NANOS).toString();
    }

    /**
     * Parse an ISO-8601 duration string.
     * 
     * @param s The input string
     * @return an instance of Duration representative of the input string.
     */
    public static Duration fromString(String s) {
        // Parse ISO 8601 compliant duration string
        try {
            return new Duration(java.time.Duration.parse(s).toNanos());
        } catch (NullPointerException | DateTimeParseException e) {
            throw new DeserializationException("Could not parse duration", e);
        }
    }

    // Arithmetic operations
    public TimeStamp add(TimeStamp t) {
        return new TimeStamp(value + t.getValue());
    }

    public Duration add(Duration d) {
        return new Duration(value + d.getValue());
    }

    public Duration subtract(Duration d) {
        return new Duration(value - d.getValue());
    }

    public Duration multiply(Number n) {
        return new Duration(value * n.longValue());
    }

    public Duration divide(Number n) {
        return new Duration(value / n.longValue());
    }

    public long divide(Duration n) {
        return value / n.value;
    }

    // TODO mod, remainder, others?
}
