package io.ciera.runtime.summit2.types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import io.ciera.runtime.summit2.exceptions.TypeException;

/**
 * ModelType is the base type for all modeled types.
 */
public abstract class ModelType {

    /**
     * TODO
     * 
     * @param <R>
     * @param type
     * @return
     */
    public <R extends Object> R castTo(Class<R> type) {
        return castTo(type, this);
    }

    /**
     * TODO
     * 
     * @param <R>
     * @param type
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <R extends Object> R castTo(Class<R> type, Object value) {
        // Handle Java primitives
        if (Long.class.equals(type)) {
            return (R) Long.valueOf(castTo(BaseLong.class, value).getValue());
        }

        // Search for a cast function in the target type
        Function<Object, R> f = null;
        try {
            Method m = type.getMethod("getCastFunction", Class.class);
            f = m != null ? (Function<Object, R>) m.invoke(null, value.getClass()) : null;
            if (f != null) {
                return f.apply(value);
            } else {
                throw new TypeException(
                        "Could not cast type '" + value.getClass().getName() + "' to '" + type.getName() + "'");

            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new TypeException(
                    "Could not cast type '" + value.getClass().getName() + "' to '" + type.getName() + "'", e);
        }
    }

}