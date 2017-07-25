package ciera.statemachine;

import ciera.exceptions.CantHappenException;
import ciera.exceptions.XtumlException;

public abstract class StateMachine {
    
    private int currentState;
    protected StateEventMatrix sem;
    
    public StateMachine() {
        currentState = 1;
    }
            
    public void transition( Event e ) throws XtumlException {
        // get new state
        int newState = sem.getCell( currentState, e.getEventId() );
        // check cannot happen and ignore
        if ( StateEventMatrix.CANNOT_HAPPEN == newState )
            throw new CantHappenException( "Event cannot happen. Class number " + Integer.toString(e.getClassNumber()) + 
                                           ", Event number " + Integer.toString(e.getEventNumber()) + ", Current state" + Integer.toString(currentState) );
        else if ( StateEventMatrix.EVENT_IGNORED == newState ) {} // do nothing
        else {
            // execute state activity
            stateActivity( newState, e );
            // update current state
            currentState = newState;
        }
    }
    
    public abstract void stateActivity( int stateNum, Event e ) throws XtumlException;

}
