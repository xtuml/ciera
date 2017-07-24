package ciera.statemachine;

import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.ModelIntegrityException;
import ciera.statemachine.exceptions.CantHappenException;
import ciera.statemachine.exceptions.StateMachineException;

public abstract class StateMachine {
    
    private int currentState;
    protected StateEventMatrix sem;
    
    public StateMachine() {
        currentState = 0;
    }
            
    public void transition( Event e ) throws StateMachineException, EmptyInstanceException, ModelIntegrityException {
        // get new state
        int newState = sem.getCell( currentState, e.getEventNumber() );
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
    
    protected abstract void stateActivity( int stateNum, Event e ) throws StateMachineException, EmptyInstanceException, ModelIntegrityException;

}
