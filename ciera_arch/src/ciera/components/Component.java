package ciera.components;

import ciera.application.ApplicationThread;
import ciera.classes.InstancePopulation;
import ciera.exceptions.XtumlException;

public abstract class Component extends InstancePopulation {
    
    public Component( ApplicationThread defaultThread ) {
        if ( null != defaultThread ) addClassesAsDefaultTarget( defaultThread );
    }
    
    private void addClassesAsDefaultTarget( ApplicationThread thread ) {
        for ( Class<?> object : getClasses() ) {
            thread.addDefaultTarget( object );
        }
    }

    public abstract void initialize() throws XtumlException;

    public abstract Class<?>[] getClasses();
}
