package io.ciera.runtime.exceptions;

public class TypeConversionException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final Class<?> source;
    private final Class<?> target;

    public TypeConversionException(String message, Class<?> source, Class<?> target) {
        super(message);
        this.source = source;
        this.target = target;
    }

    public TypeConversionException(String message, Throwable cause, Class<?> source, Class<?> target) {
        super(message, cause);
        this.source = source;
        this.target = target;
    }

    public Class<?> getSource() {
        return source;
    }

    public Class<?> getTarget() {
        return target;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": [source=" + (source != null ? source.getName() : "null") + ", target="
                + (target != null ? target.getName() : "null") + "]";
    }

}
