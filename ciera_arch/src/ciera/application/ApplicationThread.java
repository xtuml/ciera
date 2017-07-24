package ciera.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import ciera.statemachine.EventTarget;

public class ApplicationThread extends Thread {
    
    private AtomicBoolean running;
    private List<EventTarget> targets;
    private List<Class<?>> defaultTargets;
    
    public ApplicationThread() {
        targets = Collections.synchronizedList( new ArrayList<EventTarget>() );
        defaultTargets = Collections.synchronizedList( new ArrayList<Class<?>>() );
        running = new AtomicBoolean( false );
    }
    
    public void addTarget( EventTarget target ) {
        synchronized ( targets ) {
            if ( targets.add( target ) ) target.setThread( this );
        }
    }
    
    public void removeTarget( EventTarget target ) {
        synchronized ( targets ) {
            if ( targets.remove( target ) ) target.setThread( null );
        }
    }
    
    public void addDefaultTarget( Class<?> object ) {
        synchronized ( defaultTargets ) {
            defaultTargets.add( object );
        }
    }
    
    public boolean defaultFor( Class<?> object ) {
        synchronized ( defaultTargets ) {
            return defaultTargets.contains( object );
        }
    }
    
    @Override
    public void run() {
        running.set( true );
        while ( running.get() ) {
            synchronized ( targets ) {
              for ( EventTarget target : targets ) {
                  target.run();
              }
            }
        }
    }
    
    public void terminate() {
        running.set( false );
    }

}
