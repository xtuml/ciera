package io.ciera.runtime.application;

import io.ciera.runtime.api.application.ExceptionHandler;
import io.ciera.runtime.application.CommandLine.CommandLineException;

public class DefaultExceptionHandler implements ExceptionHandler {

  @Override
  public void handleError(RuntimeException e) {
    if (rootCause(e) instanceof CommandLineException) {
      System.err.println(((CommandLineException) rootCause(e)).getUsageText());
    } else {
      e.printStackTrace();
    }
    System.exit(1);
  }

  @Override
  public void handleSoftError(RuntimeException e) {
    throw e;
  }

  private static Throwable rootCause(Throwable throwable) {
    if (throwable == null || throwable.getCause() == null) {
      return throwable;
    } else {
      return rootCause(throwable.getCause());
    }
  }
}
