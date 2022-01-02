package io.ciera.runtime.exceptions;

import io.ciera.runtime.domain.InstancePopulation;
import io.ciera.runtime.domain.ObjectInstance;

public class EmptyInstanceException extends InstancePopulationException {

    private static final long serialVersionUID = 1l;

    public EmptyInstanceException(String message, InstancePopulation population, ObjectInstance... instances) {
        super(message, population, instances);
    }

}
