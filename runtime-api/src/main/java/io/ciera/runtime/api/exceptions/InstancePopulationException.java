package io.ciera.runtime.api.exceptions;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.ciera.runtime.api.domain.InstancePopulation;
import io.ciera.runtime.api.domain.ObjectInstance;

public class InstancePopulationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final InstancePopulation population;
  private final ObjectInstance[] instances;

  public InstancePopulationException(
      final String message,
      final InstancePopulation population,
      final ObjectInstance... instances) {
    this(message, null, population, instances);
  }

  public InstancePopulationException(
      final String message,
      final Throwable cause,
      final InstancePopulation population,
      final ObjectInstance... instances) {
    super(message, cause);
    this.population = population;
    this.instances = instances;
  }

  public InstancePopulation getPopulation() {
    return population;
  }

  public ObjectInstance[] getInstances() {
    return instances;
  }

  @Override
  public String getMessage() {
    return super.getMessage()
        + ": [population="
        + population
        + ", instances=["
        + Stream.of(instances).map(inst -> inst.toString()).collect(Collectors.joining(", "))
        + "]]";
  }
}
