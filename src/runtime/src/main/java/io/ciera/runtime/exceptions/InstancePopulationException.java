package io.ciera.runtime.exceptions;

import io.ciera.runtime.domain.InstancePopulation;
import io.ciera.runtime.domain.ObjectInstance;

public class InstancePopulationException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final InstancePopulation population;
    private final ObjectInstance instance;

    public InstancePopulationException(String message, InstancePopulation population, ObjectInstance instance) {
        super(message);
        this.population = population;
        this.instance = instance;
    }

    public InstancePopulationException(String message, Throwable cause, InstancePopulation population,
            ObjectInstance instance) {
        super(message, cause);
        this.population = population;
        this.instance = instance;
    }

    public InstancePopulation getPopulation() {
        return population;
    }

    public ObjectInstance getInstance() {
        return instance;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": [population=" + population + ", instance=" + instance + "]";
    }

}
