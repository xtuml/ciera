package ciera.statemachine;

import java.util.concurrent.ConcurrentLinkedQueue;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.ModelIntegrityException;
import ciera.statemachine.exceptions.StateMachineException;

public abstract class EventDispatch {
    
    private ConcurrentLinkedQueue<Event> eventsToSelf;
    private ConcurrentLinkedQueue<Event> events;
    
    public void run() {
        try {
            dispatch();
        }
        catch ( StateMachineException e ) {
            // TODO exception handling
        }
        catch ( EmptyInstanceException e ) {
            // TODO exception handling
        }
        catch (ModelIntegrityException e) {
            // TODO exception handling
        }
    }
    
    public void dispatch() throws StateMachineException, EmptyInstanceException, ModelIntegrityException {
        Event e = null;
        // handle events to self first
        if ( !eventsToSelf.isEmpty() ) {
            e = eventsToSelf.remove();
        }
        else if ( !events.isEmpty() ) {
            e = events.remove();
        }
        if ( e != null ) e.getTarget().transition(e);
    }
    
    public void generateTo( Event e ) {
        if ( e.toSelf() ) eventsToSelf.add( e );
        else events.add( e );
    }

}