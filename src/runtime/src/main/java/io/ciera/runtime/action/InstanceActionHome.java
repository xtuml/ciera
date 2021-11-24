package io.ciera.runtime.action;

import io.ciera.runtime.domain.ObjectInstance;

/**
 * An instance action home represents an action home which provides a refence to
 * an associated object instance ("self").
 */
public interface InstanceActionHome extends ActionHome {

    /**
     * Get the object instance associated with invocations of this action.
     * 
     * @return {@link ObjectInstance} the object instance bound to execution of this
     *         action.
     */
    public ObjectInstance self();

}
