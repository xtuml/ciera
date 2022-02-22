package io.ciera.runtime.api.domain;

import io.ciera.runtime.api.action.ActionHome;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.types.UniqueId;

/**
 * A domain is a composite of translated model elements including classes,
 * relationships, types, functions, etc. The component provides access to
 * out-bound (required) interface messages and the instance population for every
 * action within it.
 */
public interface Domain extends ActionHome, InstancePopulation, EventTarget {

    public void initialize();

    @Override
    default public Domain getDomain() {
        return this;
    }

    @Override
    public default UniqueId getTargetId() {
        return null;
    }

}
