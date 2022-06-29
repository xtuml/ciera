package io.ciera.runtime.api.exceptions;

public class ProgramError extends ActionException {

  private static final long serialVersionUID = 1L;

  public ProgramError() {
    super();
  }

  public ProgramError(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ProgramError(final String message) {
    super(message);
  }

  public ProgramError(final Throwable cause) {
    super(cause);
  }
}
