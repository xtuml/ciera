package io.ciera.runtime.api.exceptions;

public class TypeConversionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Class<?> source;
  private final Class<?> target;

  public TypeConversionException(
      final String message, final Class<?> source, final Class<?> target) {
    this(message, null, source, target);
  }

  public TypeConversionException(
      final String message, final Throwable cause, final Class<?> source, final Class<?> target) {
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
    return super.getMessage()
        + ": [source="
        + (source != null ? source.getName() : "null")
        + ", target="
        + (target != null ? target.getName() : "null")
        + "]";
  }
}
