package io.ciera.runtime.api.exceptions;

public class DeserializationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DeserializationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public DeserializationException(final String message) {
    super(message);
  }

  public DeserializationException(final Throwable cause) {
    super(cause);
  }
}
