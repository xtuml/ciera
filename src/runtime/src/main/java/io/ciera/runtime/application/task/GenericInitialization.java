package io.ciera.runtime.application.task;

public class GenericInitialization extends GenericTask {

  private static final long serialVersionUID = 1L;

  public GenericInitialization(Runnable command) {
    super(command);
  }

  @Override
  public int getPriority() {
    return Task.INITIALIZATION_PRIORITY;
  }
}
