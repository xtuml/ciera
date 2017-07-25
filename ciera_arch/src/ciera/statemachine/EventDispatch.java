package ciera.statemachine;

import java.util.concurrent.ConcurrentLinkedQueue;

import ciera.exceptions.XtumlException;

public class EventDispatch {
    
    private ConcurrentLinkedQueue<Event> eventsToSelf;
    private ConcurrentLinkedQueue<Event> events;
    
    public EventDispatch() {
        eventsToSelf = new ConcurrentLinkedQueue<Event>();
        events = new ConcurrentLinkedQueue<Event>();
    }
    
    public void run() {
        try {
            dispatch();
        }
        catch ( XtumlException e ) {
            // TODO exception handling
            System.err.println( "Bad 3" );
            e.printStackTrace();
        }
    }
    
    public void dispatch() throws XtumlException {
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
    
    public void generateTo( Event e ) {
        if ( e.toSelf() ) eventsToSelf.add( e );
        else events.add( e );
    }

}
