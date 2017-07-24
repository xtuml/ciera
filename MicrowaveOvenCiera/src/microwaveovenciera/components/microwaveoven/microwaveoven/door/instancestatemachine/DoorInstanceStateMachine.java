package microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine;

import microwaveovenciera.components.microwaveoven.microwaveoven.Door;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;
import ciera.statemachine.exceptions.StateMachineException;

public class DoorInstanceStateMachine extends InstanceStateMachine {
    
    private static final int Open = 1;
    private static final int Closed = 2;
    
    public DoorInstanceStateMachine( Door door ) {
        instance = door;
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { StateEventMatrix.EVENT_IGNORED, Closed },
            { Open, StateEventMatrix.EVENT_IGNORED }
        });
    }

    @Override
    protected void stateActivity(int stateNum, Event e) throws StateMachineException, EmptyInstanceException {
        if ( stateNum == Open ) {
            stateOpen( e );
        }
        else if ( stateNum == Closed ) {
            stateClosed( e );
        }
        else throw new StateMachineException( "State does not exist. " );
    }
    
    private void stateOpen( Event e ) {
    }

    private void stateClosed( Event e ) {
    }

}
