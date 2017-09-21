package ciera.application;

import ciera.components.Component;
import ciera.exceptions.XtumlException;
import ciera.util.DefaultTimeKeeper;
import ciera.util.TimeKeeper;

public abstract class XtumlApplication {
    
    public static XtumlApplication app;
    
    private XtumlExecutor applicationExecutor;
    private Component[] components;
    
    public XtumlApplication() {
        applicationExecutor = new XtumlExecutor();
        components = null;
    }
    
    public abstract void setup();
    
    public void initialize() {
        for ( Component component : components ) {
            applicationExecutor.addInitialTask(new XtumlTask() {
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
    
    public void setComponents( Component[] components ) {
        this.components = components;
    }
    
    public TimeKeeper getTimeKeeper() {
        return DefaultTimeKeeper.getInstance();
    }
    
    public XtumlExecutor getExecutor() {
        return applicationExecutor;
    }

}
