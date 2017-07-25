package ciera.statemachine;

import ciera.exceptions.StateMachineException;
import ciera.exceptions.XtumlException;

public class StateEventMatrix {
    
    public static final int CANNOT_HAPPEN  = -1;
    public static final int EVENT_IGNORED = 0;

    private int[][] matrix;
    private int numColumns;
    private int numRows;
    
    public StateEventMatrix( int[][] matrix ) {
        this.matrix = matrix;
        if ( null != matrix && matrix.length > 0 ) {
            numColumns = matrix[0].length;
            numRows = matrix.length;
        }
    }
    
    public int getCell( int stateNum, int eventNum ) throws XtumlException {
        if ( stateNum < 1 || stateNum > numRows ) throw new StateMachineException("Invalid state number: " + Integer.toString(stateNum) );
        else if ( eventNum < 1 || eventNum > numColumns ) throw new StateMachineException("Invalid event number: " + Integer.toString(eventNum) );
        else return matrix[stateNum][eventNum];
    }
    
}
