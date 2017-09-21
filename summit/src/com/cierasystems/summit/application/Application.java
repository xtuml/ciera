package com.cierasystems.summit.application;

import com.cierasystems.summit.components.IComponent;
import com.cierasystems.summit.exceptions.XtumlException;
import com.cierasystems.summit.util.DefaultTimeKeeper;
import com.cierasystems.summit.util.TimeKeeper;

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
