package ciera.statemachine;

import ciera.application.XtumlApplication;
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
            System.out.printf( "Transition started: %s [%d] %s at %d.%03d seconds\n", getKeyLetters(), currentState, sem.getStateName( currentState ),
                    XtumlApplication.app.getTimeKeeper().currentTimeMicro() / 1000000,
                    ( XtumlApplication.app.getTimeKeeper().currentTimeMicro() / 1000 ) % 1000 );
            // execute state activity
            stateActivity( newState, e );
            // update current state
            currentState = newState;
            System.out.printf( "Transition complete: %s [%d] %s at %d.%03d seconds\n", getKeyLetters(), currentState, sem.getStateName( currentState ),
                    XtumlApplication.app.getTimeKeeper().currentTimeMicro() / 1000000,
                    ( XtumlApplication.app.getTimeKeeper().currentTimeMicro() / 1000 ) % 1000 );
        }
    }
    
    public abstract void stateActivity( int stateNum, Event e ) throws XtumlException;
    public abstract String getKeyLetters();

}
