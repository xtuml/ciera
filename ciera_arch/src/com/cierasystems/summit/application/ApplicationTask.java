package ciera.application;

import java.util.concurrent.ConcurrentLinkedQueue;

import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;

public abstract class XtumlTask implements Runnable {

    private ConcurrentLinkedQueue<Event> eventsToSelf;
    private ConcurrentLinkedQueue<Event> events;
    
    public XtumlTask() {
        eventsToSelf = new ConcurrentLinkedQueue<Event>();
        events = new ConcurrentLinkedQueue<Event>();
    }
    
    public abstract void init() throws XtumlException;

    @Override
    public void run() {
        try {
            init();
        }
        catch ( XtumlException e ) {
            // TODO exception handling
            System.err.println( "Bad 4" );
            e.printStackTrace();
        }
        while ( !( eventsToSelf.isEmpty() && events.isEmpty() ) ) {
            try {
                dispatch();
            }
            catch ( XtumlException e ) {
                // TODO exception handling
                System.err.println( "Bad 3" );
                e.printStackTrace();
            }
        }
    }

    private void dispatch() throws XtumlException {
        Event e = null;
        // handle events to self first
        if ( !eventsToSelf.isEmpty() ) {
            e = eventsToSelf.remove();
        }
        else if ( !events.isEmpty() ) {
            e = events.remove();
        }
        if ( e != null ) {
            e.getTarget().transition(e);
        }
    }
    
    public void generateTo( Event e ) throws XtumlException {
        if ( e.toSelf() ) eventsToSelf.add( e );
        else events.add( e );
    }

}
