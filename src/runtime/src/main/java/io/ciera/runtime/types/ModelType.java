package io.ciera.runtime.types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import io.ciera.runtime.exceptions.DeserializationException;
import io.ciera.runtime.exceptions.TypeConversionException;

/**
 * ModelType is the base type for all modeled types.
 */
public abstract class ModelType {

    /**
     * The {@code getCastFunction} method provides a mechanism for determining a
     * sequence of conversion steps to convert from one modeled type to another. If
     * an operation is available to convert an instance of type {@code sourceType}
     * to an instance of the implementing type, a {@link Function} instance is
     * returned which will perform the conversion. If no direct conversion can be
     * found, the supertypes of the implementing type are recursively interrogated
     * to find a suitable conversion. If a conversion from the supertype is found, a
     * composed conversion is created and returned as an instance of
     * {@link Function}. This process behaves a bit like a network protocol where
     * each type only needs to know how to produce itself from its closest
     * neighbors. Through recursion, a graph traversal of composed conversions is
     * built. If no possible path is found, {@literal null} is returned.
     * 
     * @param sourceType The type from which to convert.
     * @return An instance of {@link Function} which converts from
     *         {@code sourceType} to the implementing type.
     */
    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        return null;
    }

    /**
     * Convert this instance to an instance of the type passed by {@code type}.
     * 
     * @param type The type to convert to.
     * @return an instance of {@code type}.
     * @throws {@link TypeConversionException} if no suitable conversion could be found.
     */
    public <R extends Object> R castTo(Class<R> type) {
        return castTo(type, this);
    }

    /**
     * Convert {@code value} to an instance of the type passed by {@code type}.
     * 
     * @param type  The type to convert to.
     * @param value The value to convert.
     * @return an instance of {@code type}.
     * @throws {@link TypeConversionException} if no suitable conversion could be found.
     */
    @SuppressWarnings("unchecked")
    public static <R extends Object> R castTo(Class<R> type, Object value) {
        // Handle Java primitives
        if (Boolean.class.equals(type)) {
            return (R) Boolean.valueOf(castTo(BaseBoolean.class, value).getValue());
        } else if (Byte.class.equals(type)) {
            return (R) Byte.valueOf(castTo(BaseByte.class, value).getValue());
        } else if (Character.class.equals(type)) {
            return (R) Character.valueOf(castTo(BaseCharacter.class, value).getValue());
        } else if (Double.class.equals(type)) {
            return (R) Double.valueOf(castTo(BaseDouble.class, value).getValue());
        } else if (Integer.class.equals(type)) {
            return (R) Integer.valueOf(castTo(BaseInteger.class, value).getValue());
        } else if (Long.class.equals(type)) {
            return (R) Long.valueOf(castTo(BaseLong.class, value).getValue());
        } else if (Short.class.equals(type)) {
            return (R) Short.valueOf(castTo(BaseShort.class, value).getValue());
        } else if (String.class.equals(type)) {
            return (R) String.valueOf(castTo(BaseString.class, value).getValue());
        }

        // Search for a cast function in the target type
        Function<Object, R> f = null;
        try {
            Method m = type.getMethod("getCastFunction", Class.class);
            f = m != null ? (Function<Object, R>) m.invoke(null, value.getClass()) : null;
            if (f != null) {
                return f.apply(value);
            } else {
                throw new TypeConversionException(
                        "Could not cast type '" + value.getClass().getName() + "' to '" + type.getName() + "'", value.getClass(), type);

            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new TypeConversionException(
                    "Could not cast type '" + value.getClass().getName() + "' to '" + type.getName() + "'", e, value.getClass(), type);
        }
    }

    /**
     * All modeled types must implment the {@code fromString} method to provide
     * deserialization. The default is to throw an exception.
     * 
     * @param s The string to deserialize.
     * @return An instance of the type being deserialized.
     */
    public static ModelType fromString(String s) {
        throw new DeserializationException("Unknown type is not deserializable");
    }

}