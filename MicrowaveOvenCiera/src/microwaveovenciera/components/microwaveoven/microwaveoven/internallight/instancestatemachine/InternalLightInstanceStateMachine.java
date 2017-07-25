package microwaveovenciera.components.microwaveoven.microwaveoven.internallight.instancestatemachine;

import ciera.exceptions.StateMachineException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;

public class InternalLightInstanceStateMachine extends InstanceStateMachine {
    
    private static final int Off = 1;
    private static final int On = 2;
    
    public InternalLightInstanceStateMachine() {
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { On, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, Off }
        });
    }

    @Override
    public void stateActivity(int stateNum, Event e) throws XtumlException {
        if ( stateNum == Off ) {
            stateOff( e );
        }
        else if ( stateNum == On ) {
            stateOn( e );
        }
        else throw new StateMachineException( "State does not exist. " );
    }
    
    private void stateOff( Event e ) {
    }

    private void stateOn( Event e ) {
    }

}
