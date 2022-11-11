package io.ciera.runtime.api;

public interface TaskSupplier {

  Runnable getTask();

  long millisToNextTask();
}
