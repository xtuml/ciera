package io.ciera.runtime.types;

import java.util.function.Function;

import io.ciera.runtime.exceptions.DeserializationException;

public class BaseCharacter extends ModelType implements Comparable<Object> {

    private char value;

    public BaseCharacter() {
        this('\0');
    }

    public BaseCharacter(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        return Character.compare(value, ModelType.castTo(Character.class, o));
    }

    public static <T extends Object> Function<T, ModelType> getCastFunction(Class<T> sourceType) {
        // Direct cast from a subclass
        if (BaseCharacter.class.isAssignableFrom(sourceType)) {
            return o -> (BaseCharacter) o;
        }

        // Special conversion for Java primitive shadow subclass
        if (Character.class.equals(sourceType)) {
            return o -> new BaseCharacter((char) o);
        }

        // Didn't find any applicable cast functions
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return ModelType.castTo(BaseCharacter.class, o).value == value;
    }

    @Override
    public int hashCode() {
        return Character.hashCode(value);
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }

    public static BaseCharacter fromString(String s) {
        try {
            return new BaseCharacter(s.charAt(0));
        } catch (NullPointerException | StringIndexOutOfBoundsException e) {
            throw new DeserializationException("Could not parse character", e);
        }
    }

}
