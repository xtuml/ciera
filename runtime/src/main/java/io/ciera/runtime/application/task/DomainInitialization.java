package io.ciera.runtime.application.task;

import io.ciera.runtime.api.domain.Domain;

public class DomainInitialization extends Task {

  private static final long serialVersionUID = 1L;

  private final Domain domain;

  public DomainInitialization(Domain domain) {
    this.domain = domain;
  }

  @Override
  public void run() {
    domain.initialize();
  }

  @Override
  public int getPriority() {
    return Task.INITIALIZATION_PRIORITY;
  }
}
