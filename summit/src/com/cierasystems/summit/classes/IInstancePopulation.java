package com.cierasystems.summit.classes;

import java.util.Map;

import com.cierasystems.summit.exceptions.XtumlException;

public interface IInstancePopulation {

    public void setInstancePopulationMap( Map<Class<?>, IInstanceSet> instancePopulation );
    public IInstanceSet getInstanceSet( Class<?> type );
    public <T extends IModelInstance> T createObjectInstance( T instance ) throws XtumlException;
    public <T extends IModelInstance> void deleteObjectInstance( T instance ) throws XtumlException;
    public IInstanceSet getNewInstanceSetForClass( Class<?> type );
    public Class<?>[] getClasses();

}
