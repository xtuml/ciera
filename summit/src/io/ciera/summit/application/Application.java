package io.ciera.summit.application;

import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.util.DefaultTimeKeeper;
import io.ciera.summit.util.TimeKeeper;

public abstract class Application {
    
    public static Application app;
    
    private ApplicationExecutor applicationExecutor;
    private IComponent[] components;
    
    public Application() {
        applicationExecutor = new ApplicationExecutor();
        components = null;
    }
    
    public abstract void setup();
    
    public void initialize() {
        for ( IComponent component : components ) {
            applicationExecutor.addInitialTask(new ApplicationTask() {
                @Override
                public void init() throws XtumlException {
                    component.initialize();
                }
            });
        }
    }
    
    public void start() {
        applicationExecutor.start();
    }

    public void stop() {
        applicationExecutor.stop();
    }
    
    public void setNumberThreads( int size ) {
        applicationExecutor.setNumberThreads( size );
    }
    
    public void setComponents( IComponent[] components ) {
        this.components = components;
    }
    
    public TimeKeeper getTimeKeeper() {
        return DefaultTimeKeeper.getInstance();
    }
    
    public ApplicationExecutor getExecutor() {
        return applicationExecutor;
    }

}
