package microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine.copy;

import microwaveovenciera.components.microwaveoven.microwaveoven.MagnetronTube;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;
import ciera.statemachine.exceptions.StateMachineException;

public class MagnetronTubeInstanceStateMachine extends InstanceStateMachine {
    
    private static final int OutputPowerStableAndOff = 1;
    private static final int ReducingOutputPower = 2;
    private static final int RaisingOutputPower = 3;
    private static final int OutputPowerStableAndOn = 4;
    
    public MagnetronTubeInstanceStateMachine( MagnetronTube magnetronTube ) {
        instance = magnetronTube;
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { RaisingOutputPower, ReducingOutputPower, OutputPowerStableAndOn, StateEventMatrix.EVENT_IGNORED },
            { RaisingOutputPower, ReducingOutputPower, OutputPowerStableAndOn, OutputPowerStableAndOff },
            { RaisingOutputPower, ReducingOutputPower, OutputPowerStableAndOn, OutputPowerStableAndOff },
            { RaisingOutputPower, ReducingOutputPower, StateEventMatrix.EVENT_IGNORED, OutputPowerStableAndOff },
        });
    }

    @Override
    protected void stateActivity(int stateNum, Event e) throws StateMachineException, EmptyInstanceException {
        if ( stateNum == OutputPowerStableAndOff ) {
            stateOutputPowerStableAndOff( e );
        }
        else if ( stateNum == ReducingOutputPower ) {
            stateReducingOutputPower( e );
        }
        else if ( stateNum == RaisingOutputPower ) {
            stateRaisingOutputPower( e );
        }
        else if ( stateNum == OutputPowerStableAndOn ) {
            stateOutputPowerStableAndOn( e );
        }
        else throw new StateMachineException( "State does not exist. " );
    }
    
    private void stateOutputPowerStableAndOff( Event e ) {
    }

    private void stateReducingOutputPower( Event e ) {
    }

    private void stateRaisingOutputPower( Event e ) {
    }
    
    private void stateOutputPowerStableAndOn( Event e ) {
    }

}
