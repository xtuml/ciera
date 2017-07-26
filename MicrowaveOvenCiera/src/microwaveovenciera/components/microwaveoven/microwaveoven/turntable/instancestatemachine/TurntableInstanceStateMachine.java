package microwaveovenciera.components.microwaveoven.microwaveoven.turntable.instancestatemachine;

import ciera.exceptions.StateMachineException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;

public class TurntableInstanceStateMachine extends InstanceStateMachine {
    
    private static final int Stationary = 1;
    private static final int Rotating = 2;
    private static final String[] stateNames = new String[] { "Non Existent", "Stationary", "Rotating" };
    
    public TurntableInstanceStateMachine() {
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { Rotating, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, Stationary }
        }) {
            @Override
            public String getStateName( int state ) {
                return stateNames[state];
            }
        };
    }

    @Override
    public void stateActivity(int stateNum, Event e) throws XtumlException {
        if ( stateNum == Stationary ) {
            stateStationary( e );
        }
        else if ( stateNum == Rotating ) {
            stateRotating( e );
        }
        else throw new StateMachineException( "State does not exist. " );
    }
    
    private void stateStationary( Event e ) {
    }

    private void stateRotating( Event e ) {
    }

}
