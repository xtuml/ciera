package io.ciera.runtime.api;

public interface ExceptionHandler {

  void handleError(RuntimeException e);

  void handleSoftError(RuntimeException e);
}
