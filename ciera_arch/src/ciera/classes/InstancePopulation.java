package ciera.classes;

import java.util.Map;

import ciera.exceptions.InstancePopulationException;
import ciera.exceptions.XtumlException;

public abstract class InstancePopulation {

    private Map<Class<?>, InstanceSet> instancePopulation;

    public void setInstancePopulation( Map<Class<?>, InstanceSet> instancePopulation ) {
        this.instancePopulation = instancePopulation;
    }

    public InstanceSet getInstanceSet( Class<?> object ) {
        return instancePopulation.get( object );
    }

    public <T extends ModelInstance> T createObjectInstance( T instance ) throws XtumlException {
        InstanceSet instanceSet = instancePopulation.get( instance.getClass() );
        if ( null != instanceSet ) {
            instance.setContext( this );
            instanceSet.add( instance );
            return instance;
        }
        else throw new InstancePopulationException( "Class does not exist within this population." );
    }

    public <T extends ModelInstance> void deleteObjectInstance( T instance ) throws XtumlException {
        InstanceSet instanceSet = instancePopulation.get( instance.getClass() );
        if ( null != instanceSet ) {
            instance.setContext( null );
            instanceSet.remove( instance );
            instance.delete();
        }
        else throw new InstancePopulationException( "Class does not exist within this population." );
    }

}
