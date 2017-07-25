package ciera.components;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ciera.application.ApplicationThread;
import ciera.classes.InstancePopulation;
import ciera.classes.InstanceSet;
import ciera.exceptions.XtumlException;

public abstract class Component extends InstancePopulation {
    
    public Component( ApplicationThread defaultThread ) {
        if ( null != defaultThread ) addClassesAsDefaultTarget( defaultThread );
        Map<Class<?>, InstanceSet> instancePopulation = new ConcurrentHashMap<Class<?>, InstanceSet>();
        for ( Class<?> object : getClasses() ) {
            instancePopulation.put( object , InstanceSet.getNewInstanceSetForClass( object ) );
        }
        this.setInstancePopulation( instancePopulation );
    }
    
    private void addClassesAsDefaultTarget( ApplicationThread thread ) {
        for ( Class<?> object : getClasses() ) {
            thread.addDefaultTarget( object );
        }
    }

    public abstract void initialize() throws XtumlException;

    public abstract Class<?>[] getClasses();
}
