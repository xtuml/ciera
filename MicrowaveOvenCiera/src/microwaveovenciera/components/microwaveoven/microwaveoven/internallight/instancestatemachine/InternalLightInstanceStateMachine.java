package microwaveovenciera.components.microwaveoven.microwaveoven.internallight.instancestatemachine;

import microwaveovenciera.components.microwaveoven.microwaveoven.InternalLight;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;
import ciera.statemachine.exceptions.StateMachineException;

public class InternalLightInstanceStateMachine extends InstanceStateMachine {
    
    private static final int Off = 1;
    private static final int On = 2;
    
    public InternalLightInstanceStateMachine( InternalLight internalLight ) {
        instance = internalLight;
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { On, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, Off }
        });
    }

    @Override
    protected void stateActivity(int stateNum, Event e) throws StateMachineException, EmptyInstanceException {
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
