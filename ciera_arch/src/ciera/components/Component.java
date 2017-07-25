package ciera.components;

import java.util.Map;

import ciera.classes.InstancePopulation;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.exceptions.InstancePopulationException;

public class Component implements InstancePopulation {
    
    private Map<Class<?>, InstanceSet> instancePopulation;
    
    public void setInstancePopulation( Map<Class<?>, InstanceSet> instancePopulation ) {
        this.instancePopulation = instancePopulation;
    }

    @Override
    public InstanceSet getInstanceSet( Class<?> object ) {
        return instancePopulation.get( object );
    }

    @Override
    public void addInstanceToPopulation( ModelInstance instance ) throws InstancePopulationException {
        InstanceSet instanceSet = instancePopulation.get( instance.getClass() );
        if ( null != instanceSet ) instanceSet.add( instance );
        else throw new InstancePopulationException( "Class does not exist within this population." );
    }

    @Override
    public void removeInstanceFromPopulation( ModelInstance instance ) throws InstancePopulationException {
        InstanceSet instanceSet = instancePopulation.get( instance.getClass() );
        if ( null != instanceSet ) instanceSet.remove( instance );
        else throw new InstancePopulationException( "Class does not exist within this population." );
    }

}
