package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.InstancePopulation;
import io.ciera.runtime.api.ObjectInstance;

public class InvalidRelationshipException extends InstancePopulationException {

  private static final long serialVersionUID = 1L;

  public InvalidRelationshipException(
      final String message,
      final InstancePopulation population,
      final ObjectInstance... instances) {
    super(message, population, instances);
  }
}
