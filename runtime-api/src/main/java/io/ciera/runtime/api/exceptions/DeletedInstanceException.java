package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.InstancePopulation;
import io.ciera.runtime.api.ObjectInstance;

public class DeletedInstanceException extends InstancePopulationException {

  private static final long serialVersionUID = 1L;

  public DeletedInstanceException(
      final String message,
      final InstancePopulation population,
      final ObjectInstance... instances) {
    super(message, population, instances);
  }
}
