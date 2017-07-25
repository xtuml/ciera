package microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine;

import microwaveovenciera.components.microwaveoven.microwaveoven.Door;
import microwaveovenciera.components.microwaveoven.microwaveoven.Oven;
import microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine.CancelCooking;
import ciera.exceptions.StateMachineException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;

public class DoorInstanceStateMachine extends InstanceStateMachine {
    
    private static final int Open = 1;
    private static final int Closed = 2;
    
    public DoorInstanceStateMachine() {
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { StateEventMatrix.EVENT_IGNORED, Closed },
            { Open, StateEventMatrix.EVENT_IGNORED }
        });
    }

    @Override
    protected void stateActivity(int stateNum, Event e) throws XtumlException {
        if ( stateNum == Open ) {
            stateOpen( e );
        }
        else if ( stateNum == Closed ) {
            stateClosed( e );
        }
        else throw new StateMachineException( "State does not exist. " );
    }
    
    private void stateOpen( Event e ) throws XtumlException {
        Door self = (Door)getInstance();
        // assign self.is_secure = false;
        self.setM_is_secure( false );
        // select one oven related by self->MO_O[R4];
        Oven oven = self.selectOneMO_OOnR4();
        // generate MO_O4:'cancel_cooking' to oven;
        oven.generateTo( new CancelCooking() );
    }

    private void stateClosed( Event e ) throws XtumlException {
        Door self = (Door)getInstance();
        // assign self.is_secure = true;
        self.setM_is_secure( true );
    }

}
