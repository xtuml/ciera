package io.ciera.runtime.instanceloading.generic;

import io.ciera.runtime.instanceloading.generic.util.LOAD;

/**
 * Represents a hand written instance loader that can be initiated by a call to
 * the {@link io.ciera.runtime.instanceloading.generic.util.LOAD LOAD} external
 * entity. Implement this interface to create a generic instance loader.
 */
public interface IGenericLoader {
	
    /**
     * Initiate a load.
     *
     * @param loader an instance of the {@link
     * io.ciera.runtime.instanceloading.generic.util.LOAD LOAD} external entity
     * which provides a hook back into the instance population of the containing
     * component.
     * @param args an argument list passed from the OAL initiation
     * @see io.ciera.runtime.instanceloading.generic.util.LOAD
     */
	public void load(LOAD loader, String[] args);

}
