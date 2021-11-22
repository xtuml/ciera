package io.ciera.runtime.summit2.types;

import java.util.function.Function;

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
