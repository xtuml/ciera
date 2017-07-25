package ciera.statemachine;

import ciera.exceptions.StateMachineException;
import ciera.exceptions.XtumlException;

public class StateEventMatrix {
    
    public static final int CANNOT_HAPPEN = -2;
    public static final int EVENT_IGNORED = -1;
    public static final int NON_EXSISTENT = 0;

    private int[][] matrix;
    private int numEvents;
    private int numStates;
    
    public StateEventMatrix( int[][] matrix ) {
        this.matrix = matrix;
        if ( null != matrix && matrix.length > 0 ) {
            numEvents = matrix[0].length;
            numStates = matrix.length;
        }
    }
    
    public int getCell( int stateNum, int eventNum ) throws XtumlException {
        if ( stateNum < 0 || stateNum >= numStates ) throw new StateMachineException("Invalid state number: " + Integer.toString(stateNum) );
        else if ( eventNum < 0 || eventNum > numEvents ) throw new StateMachineException("Invalid event number: " + Integer.toString(eventNum) );
        else return matrix[stateNum][eventNum];
    }
    
}
