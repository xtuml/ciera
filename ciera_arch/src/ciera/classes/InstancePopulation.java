package ciera.classes;

import ciera.classes.exceptions.InstancePopulationException;

public interface InstancePopulation {

    public InstanceSet getInstanceSet( Class<?> object );
    public void addInstanceToPopulation( ModelInstance instance ) throws InstancePopulationException;
    public void removeInstanceFromPopulation( ModelInstance instance ) throws InstancePopulationException;

}
