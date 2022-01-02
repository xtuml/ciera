package io.ciera.runtime.exceptions;

import io.ciera.runtime.domain.InstancePopulation;
import io.ciera.runtime.domain.ObjectInstance;

public class DeletedInstanceException extends InstancePopulationException {

    private static final long serialVersionUID = 1l;

    public DeletedInstanceException(String message, InstancePopulation population, ObjectInstance... instances) {
        super(message, population, instances);
    }

}
