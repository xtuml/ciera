package ciera.application;

import ciera.components.Component;
import ciera.exceptions.XtumlException;
import ciera.util.DefaultTimeKeeper;
import ciera.util.TimeKeeper;

public abstract class XtumlApplication {
    
    public static XtumlApplication app;
    
    private ApplicationThread[] threadPool;
    private Component[] components;
    
    public abstract void setup();
    
    public void initialize() {
        for ( Component component : components ) {
            try {
                component.initialize();
            } catch ( XtumlException e ) {
                // TODO exception handling
                System.err.println( "Bad 1" );
                e.printStackTrace();
            }
        }
    }
    
    public void start() {
        for ( ApplicationThread thread : threadPool ) {
            thread.start();
        }
    }

    public void stop() {
        for ( ApplicationThread thread : threadPool ) {
            thread.terminate();
        }
    }
    
    public void createThreadPool( int size ) {
        threadPool = new ApplicationThread[size];
        for ( int i = 0; i < size; i++ ) {
            threadPool[i] = new ApplicationThread();
        }
    }
    
    public ApplicationThread getThreadFromPool( int index ) {
        return threadPool[index];
    }
    
    public ApplicationThread getDefaultThread( Class<?> object ) {
        for ( ApplicationThread thread : threadPool ) {
            if ( thread.defaultFor( object ) ) return thread;
        }
        return null;
    }
    
    public void setComponents( Component[] components ) {
        this.components = components;
    }
    
    public TimeKeeper getTimeKeeper() {
        return DefaultTimeKeeper.getInstance();
    }

}
