package io.ciera.runtime.api.application;

public interface ExceptionHandler {

  void handleError(RuntimeException e);

  void handleSoftError(RuntimeException e);
}
