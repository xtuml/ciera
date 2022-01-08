package io.ciera.runtime.types;

import java.util.function.Function;

import io.ciera.runtime.application.SystemClock;

/**
 * The TimeStamp class represents a point in time. It is represented as a
 * quantity of nanoseconds elapsed since an epoch (reference point in time).
 * TimeStamp instances do not inherently define the epoch they are referenced
 * from, but will be interpreted by the runtime based on the current settings of
 * the {@link SystemClock}.
 */
public class TimeStamp extends ModelType {

    /**
     * Default value
     */
    public static final TimeStamp ZERO = new TimeStamp();
    
    private final long value;

    public TimeStamp() {
        this(0l);
    }

    public TimeStamp(long value) {
        this.value = value;
    }

    public TimeStamp(TimeStamp o) {
        this(o.getValue());
    }
    
    public long getValue() {
        return value;
    }

    /**
     * Create a new instance of TimeStamp from the current time and epoch set in the
     * system clock.
     * 
     * @param clock The clock to reference.
     * @return A constructed TimeStamp instance.
     */
    public static TimeStamp now(SystemClock clock) {
        return new TimeStamp(clock.getTime());
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (TimeStamp.class.isAssignableFrom(sourceType)) {
            return o -> (TimeStamp) o;
        }
        
        // TODO conversion from other numeric types

        // Didn't find any applicable cast functions
        return null;
    }

    public static TimeStamp fromString(String s) {
        return new TimeStamp(Long.parseLong(s));
    }
    
    // Arithmetic operations
    public TimeStamp add(Duration d) {
        return new TimeStamp(value + d.getValue());
    }

    public TimeStamp subtract(Duration d) {
        return new TimeStamp(value - d.getValue());
    }

    public Duration subtract(TimeStamp t) {
        return new Duration(value - t.getValue());
    }

}
