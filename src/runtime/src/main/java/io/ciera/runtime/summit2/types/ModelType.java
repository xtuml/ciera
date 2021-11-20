package io.ciera.runtime.summit2.types;

import java.util.function.Function;

import io.ciera.runtime.summit2.exceptions.TypeException;

/**
 * ModelType is the base type for all modeled types.
 */
public abstract class ModelType {

    public static <R extends Object> Function<ModelType, R> getCastFunctionForType(Class<R> type, Class<?> ignoreClass) {
        // The default implementation does not define any valid type casts
        return null;
    }

    /**
     * TODO returns a cast function that can be applied to convert the current type
     * to the type passed in by the argument returns null if no suitable cast is
     * found
     * 
     * @param <T>
     * @param type
     * @return
     */
    public <R extends Object> Function<ModelType, R> getCastFunction(Class<R> type) {
        return null;
    }

    /**
     * TODO
     * 
     * @param <R>
     * @param type
     * @return
     */
    public <R extends Object> R castTo(Class<R> type) {
        Function<ModelType, R> f = getCastFunction(type);
        if (f != null) {
            return f.apply(this);
        } else {
            throw new TypeException("Could not cast type '" + getClass().getName() + "' to '" + type.getName() + "'");
        }
    }

    /**
     * TODO
     * 
     * @param <R>
     * @param type
     * @param value
     * @return
     */
    public static <R extends Object> R castTo(Class<R> type, Object value) {
        // Handle Java primitives
        if (value instanceof Double || value instanceof Float) {
            return new BaseNumeric(((Number) value).longValue(), Math.abs(((Number) value).doubleValue() % 1))
                    .castTo(type);
        } else if (value instanceof Number) {
            return new BaseNumeric(((Number) value).longValue(), 0d).castTo(type);
        } else if (value instanceof Character) {
            // TODO
        } else if (value instanceof String) {
            // TODO
        }

        // Handle modeled types
        if (value instanceof ModelType) {
            return ((ModelType) value).castTo(type);
        }

        // Default to an exception
        throw new TypeException("Could not cast type '" + value.getClass().getName() + "' to '" + type.getName() + "'");
    }

}