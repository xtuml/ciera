package ciera.components;

import ciera.application.ApplicationThread;
import ciera.classes.InstancePopulation;

public abstract class Component extends InstancePopulation {
    
    public Component( ApplicationThread defaultThread ) {
        if ( null != defaultThread ) addClassesAsDefaultTarget( defaultThread );
    }
    
    private void addClassesAsDefaultTarget( ApplicationThread thread ) {
        for ( Class<?> object : getClasses() ) {
            thread.addDefaultTarget( object );
        }
    }

    public abstract void initialize();

    public abstract Class<?>[] getClasses();
}
