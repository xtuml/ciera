package microwaveovenciera.components.microwaveoven.microwaveoven.turntable.instancestatemachine;

import ciera.exceptions.StateMachineException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;

public class TurntableInstanceStateMachine extends InstanceStateMachine {
    
    private static final int Stationary = 1;
    private static final int Rotating = 2;
    
    public TurntableInstanceStateMachine() {
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { Rotating, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, Stationary }
        });
    }

    @Override
    protected void stateActivity(int stateNum, Event e) throws XtumlException {
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
