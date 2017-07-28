package ciera.components;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ciera.classes.InstancePopulation;
import ciera.classes.InstanceSet;
import ciera.exceptions.XtumlException;

public abstract class Component extends InstancePopulation {
    
    public Component() {
        Map<Class<?>, InstanceSet> instancePopulation = new ConcurrentHashMap<Class<?>, InstanceSet>();
        for ( Class<?> object : getClasses() ) {
            instancePopulation.put( object , InstanceSet.getNewInstanceSetForClass( object ) );
        }
        this.setInstancePopulation( instancePopulation );
    }
    
    public abstract void initialize() throws XtumlException;

    public abstract Class<?>[] getClasses();
}
