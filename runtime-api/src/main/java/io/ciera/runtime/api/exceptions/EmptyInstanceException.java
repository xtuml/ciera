package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.InstancePopulation;
import io.ciera.runtime.api.ObjectInstance;

public class EmptyInstanceException extends InstancePopulationException {

  private static final long serialVersionUID = 1L;

  public EmptyInstanceException(
      final String message,
      final InstancePopulation population,
      final ObjectInstance... instances) {
    super(message, population, instances);
  }
}
