package io.ciera.runtime.exceptions;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.ciera.runtime.domain.InstancePopulation;
import io.ciera.runtime.domain.ObjectInstance;

public class InstancePopulationException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final InstancePopulation population;
    private final ObjectInstance[] instances;

    public InstancePopulationException(String message, InstancePopulation population, ObjectInstance... instances) {
        this(message, null, population, instances);
    }

    public InstancePopulationException(String message, Throwable cause, InstancePopulation population,
            ObjectInstance... instances) {
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
        return super.getMessage() + ": [population=" + population + ", instances=["
                + Stream.of(instances).map(inst -> inst.toString()).collect(Collectors.joining(", ")) + "]]";
    }

}
