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
public class TimeStamp extends BaseLong {

    public TimeStamp() {
        super();
    }

    public TimeStamp(long value) {
        super(value);
    }

    public TimeStamp(BaseLong o) {
        this(o.getValue());
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

        // Direct conversion from superclass
        if (BaseLong.class.equals(sourceType)) {
            return o -> new TimeStamp((BaseLong) o);
        }

        // Search in superclass for indirect conversion
        Function<T, ModelType> f = BaseLong.getCastFunction(sourceType);
        if (f != null) {
            return o -> getCastFunction(BaseLong.class).apply((BaseLong) f.apply(o));
        }

        // Didn't find any applicable cast functions
        return null;
    }

    public static TimeStamp fromString(String s) {
        return new TimeStamp(BaseLong.fromString(s));
    }

}
