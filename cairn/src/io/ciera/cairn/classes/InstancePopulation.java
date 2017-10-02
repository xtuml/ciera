package io.ciera.cairn.classes;

import java.util.Map;

import io.ciera.summit.classes.IInstancePopulation;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.exceptions.InstancePopulationException;
import io.ciera.summit.exceptions.XtumlException;

public abstract class InstancePopulation implements IInstancePopulation {

    private Map<Class<?>, IInstanceSet> instancePopulation;

    @Override
    public void setInstancePopulationMap( Map<Class<?>, IInstanceSet> instancePopulation ) {
        this.instancePopulation = instancePopulation;
    }

    @Override
    public IInstanceSet getInstanceSet( Class<?> type ) {
        return instancePopulation.get( type );
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
