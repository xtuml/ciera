package com.cierasystems.cairn.classes;

import java.util.Map;

import com.cierasystems.summit.classes.IInstancePopulation;
import com.cierasystems.summit.classes.IInstanceSet;
import com.cierasystems.summit.classes.IModelInstance;
import com.cierasystems.summit.exceptions.InstancePopulationException;
import com.cierasystems.summit.exceptions.XtumlException;

public abstract class InstancePopulation implements IInstancePopulation {

    private Map<Class<?>, IInstanceSet> instancePopulation;

    @Override
    public void setInstancePopulation( Map<Class<?>, IInstanceSet> instancePopulation ) {
        this.instancePopulation = instancePopulation;
    }

    @Override
    public IInstanceSet getInstanceSet( Class<?> object ) {
        return instancePopulation.get( object );
    }

    @Override
    public <T extends IModelInstance> T createObjectInstance( T instance ) throws XtumlException {
        IInstanceSet instanceSet = instancePopulation.get( instance.getClass() );
        if ( null != instanceSet ) {
            instance.setContext( this );
            instanceSet.add( instance );
            return instance;
        }
        else throw new InstancePopulationException( "Class does not exist within this population." );
    }

    @Override
    public <T extends IModelInstance> void deleteObjectInstance( T instance ) throws XtumlException {
        IInstanceSet instanceSet = instancePopulation.get( instance.getClass() );
        if ( null != instanceSet ) {
            instance.setContext( null );
            instanceSet.remove( instance );
            instance.delete();
        }
        else throw new InstancePopulationException( "Class does not exist within this population." );
    }

}
