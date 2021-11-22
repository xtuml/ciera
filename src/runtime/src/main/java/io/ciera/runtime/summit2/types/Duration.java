package io.ciera.runtime.summit2.types;

import java.time.temporal.ChronoUnit;
import java.util.function.Function;

import io.ciera.runtime.summit2.exceptions.DeserializationException;

import java.time.format.DateTimeParseException;

/**
 * The Duration class represents a period of time. It is represented as a
 * quantity of microseconds. Durations can be represented as ISO-8601 strings.
 */
public class Duration extends BaseLong {

    public Duration() {
        super();
    }

    public Duration(long value) {
        super(value);
    }

    public Duration(BaseLong o) {
        this(o.getValue());
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (Duration.class.isAssignableFrom(sourceType)) {
            return o -> (Duration) o;
        }

        // Direct conversion from superclass
        if (BaseLong.class.equals(sourceType)) {
            return o -> new Duration((BaseLong) o);
        }

        // Search in superclass for indirect conversion
        Function<T, ModelType> f = BaseLong.getCastFunction(sourceType);
        if (f != null) {
            return o -> getCastFunction(BaseLong.class).apply((BaseLong) f.apply(o));
        }

        // Didn't find any applicable cast functions
        return null;
    }

    /**
     * Override the 'toString' method to produce an ISO-8601 compliant duration
     * string.
     */
    @Override
    public String toString() {
        // Create ISO 8601 compliant duration string
        return java.time.Duration.of(getValue(), ChronoUnit.MICROS).toString();
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
            return new Duration(java.time.Duration.parse(s).toNanos() / 1000L);
        } catch (NullPointerException | DateTimeParseException e) {
            throw new DeserializationException("Could not parse duration", e);
        }
    }

}
