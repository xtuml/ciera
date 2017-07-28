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
            long startTime = XtumlApplication.app.getTimeKeeper().currentTimeMicro();
            // execute state activity
            stateActivity( newState, e );
            long endTime = XtumlApplication.app.getTimeKeeper().currentTimeMicro();
            System.out.printf( "TXN: %-5s %-49s -> %s\n", getKeyLetters(), String.format( "[%d] %-33s (t=%d.%03ds)", currentState, sem.getStateName( currentState ),
                    startTime / 1000000, ( startTime / 1000 ) % 1000 ), String.format( "[%d] %-33s (t=%d.%03ds)", newState, sem.getStateName( newState ),
                    endTime / 1000000, ( endTime / 1000 ) % 1000 ) );
            // update current state
            currentState = newState;
        }
    }
    
    public abstract void stateActivity( int stateNum, Event e ) throws XtumlException;
    public abstract String getKeyLetters();

}
