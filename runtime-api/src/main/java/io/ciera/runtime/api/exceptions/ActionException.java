package io.ciera.runtime.api.exceptions;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ActionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Object data;

  private final List<StackTraceElement> stack;

  public ActionException() {
    this(null, null, null);
  }

  public ActionException(final String message) {
    this(message, null, null);
  }

  public ActionException(final Throwable cause) {
    this(null, null, cause);
  }

  public ActionException(final Object data) {
    this(null, data, null);
  }

  public ActionException(final String message, final Throwable cause) {
    this(message, null, cause);
  }

  public ActionException(final Object data, final Throwable cause) {
    this(null, data, cause);
  }

  private ActionException(final String message, final Object data, final Throwable cause) {
    super(message);
    this.data = data;
    stack = new LinkedList<>();
    if (cause != null) {
      final List<StackTraceElement> trace = List.of(getStackTrace());
      cause.setStackTrace(
          Stream.of(cause.getStackTrace())
              .filter(ste -> !trace.contains(ste))
              .toArray(StackTraceElement[]::new));
      initCause(cause);
    }
  }

  public void updateStack(
      final String parentName, final String bodyName, final String fileName, final int lineNumber) {
    stack.add(new StackTraceElement(parentName, bodyName, fileName, lineNumber));
    setStackTrace(stack.toArray(new StackTraceElement[0]));
  }

  public Object getData() {
    return data;
  }
}
