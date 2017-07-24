package microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine;

import microwaveovenciera.components.microwaveoven.microwaveoven.Turntable;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;
import ciera.statemachine.exceptions.StateMachineException;

public class OvenInstanceStateMachine extends InstanceStateMachine {
    
    private static final int AwaitingCookingRequest = 1;
    private static final int EnsuringSafeToCook = 2;
    private static final int Cooking = 3;
    private static final int CookingSuspended = 4;
    private static final int SignallingCookingPeriodOver = 5;
    private static final int AssigningCookingPeriod = 6;
    
    public OvenInstanceStateMachine( Turntable turntable ) {
        instance = turntable;
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, AssigningCookingPeriod },
            { StateEventMatrix.EVENT_IGNORED, EnsuringSafeToCook, AwaitingCookingRequest, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, Cooking, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, CookingSuspended, StateEventMatrix.EVENT_IGNORED, SignallingCookingPeriodOver, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, EnsuringSafeToCook, AwaitingCookingRequest, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, AwaitingCookingRequest, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, AwaitingCookingRequest, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, EnsuringSafeToCook, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED }
        });
    }

    @Override
    protected void stateActivity(int stateNum, Event e) throws StateMachineException, EmptyInstanceException {
        if ( stateNum == AwaitingCookingRequest ) {
            stateAwaitingCookingRequest( e );
        }
        else if ( stateNum == EnsuringSafeToCook ) {
            stateEnsuringSafeToCook( e );
        }
        else if ( stateNum == Cooking ) {
            stateCooking( e );
        }
        else if ( stateNum == CookingSuspended ) {
            stateCookingSuspended( e );
        }
        else if ( stateNum == SignallingCookingPeriodOver ) {
            stateSignallingCookingPeriodOver( e );
        }
        else if ( stateNum == AssigningCookingPeriod ) {
            stateAssigningCookingPeriod( e );
        }
        else throw new StateMachineException( "State does not exist. " );
    }
    
    private void stateAwaitingCookingRequest( Event e ) {
    }

    private void stateEnsuringSafeToCook( Event e ) {
    }

    private void stateCooking( Event e ) {
    }

    private void stateCookingSuspended( Event e ) {
    }

    private void stateSignallingCookingPeriodOver( Event e ) {
    }

    private void stateAssigningCookingPeriod( Event e ) {
    }

}
