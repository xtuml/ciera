package io.ciera.runtime.api.domain;

import io.ciera.runtime.api.action.ActionHome;

/**
 * A domain is a composite of translated model elements including classes,
 * relationships, types, functions, etc. The component provides access to
 * out-bound (required) interface messages and the instance population for every
 * action within it.
 */
public interface Domain extends ActionHome, InstancePopulation {

    public void initialize();

}
