package ciera.classes;

import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.InstancePopulationException;

public interface InstancePopulation {

    public InstanceSet getInstanceSet( Class<?> object );
    public <T extends ModelInstance> T createObjectInstance( T instance ) throws InstancePopulationException;
    public <T extends ModelInstance> void deleteObjectInstance( T instance ) throws InstancePopulationException, EmptyInstanceException;

}
