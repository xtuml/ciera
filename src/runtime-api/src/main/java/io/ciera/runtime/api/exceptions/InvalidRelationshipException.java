package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.domain.InstancePopulation;
import io.ciera.runtime.api.domain.ObjectInstance;

public class InvalidRelationshipException extends InstancePopulationException {

    private static final long serialVersionUID = 1l;

    public InvalidRelationshipException(String message, InstancePopulation population, ObjectInstance... instances) {
        super(message, population, instances);
    }

}
