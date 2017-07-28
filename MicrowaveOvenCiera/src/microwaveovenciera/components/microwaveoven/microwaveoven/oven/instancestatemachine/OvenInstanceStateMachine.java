package microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine;

import microwaveovenciera.components.microwaveoven.externalentities.TIM;
import microwaveovenciera.components.microwaveoven.microwaveoven.Beeper;
import microwaveovenciera.components.microwaveoven.microwaveoven.Door;
import microwaveovenciera.components.microwaveoven.microwaveoven.InternalLight;
import microwaveovenciera.components.microwaveoven.microwaveoven.MagnetronTube;
import microwaveovenciera.components.microwaveoven.microwaveoven.Oven;
import microwaveovenciera.components.microwaveoven.microwaveoven.Turntable;
import microwaveovenciera.components.microwaveoven.microwaveoven.beeper.instancestatemachine.StartBeeping;
import microwaveovenciera.components.microwaveoven.microwaveoven.beeper.instancestatemachine.StopBeeping;
import microwaveovenciera.components.microwaveoven.microwaveoven.internallight.instancestatemachine.SwitchOff;
import microwaveovenciera.components.microwaveoven.microwaveoven.internallight.instancestatemachine.SwitchOn;
import microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine.PowerOff;
import microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine.PowerOn;
import microwaveovenciera.components.microwaveoven.microwaveoven.turntable.instancestatemachine.Spin;
import microwaveovenciera.components.microwaveoven.microwaveoven.turntable.instancestatemachine.Stop;
import ciera.exceptions.StateMachineException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;

public class OvenInstanceStateMachine extends InstanceStateMachine {
    
    private static final int AwaitingCookingRequest = 1;
    private static final int EnsuringSafeToCook = 2;
    private static final int Cooking = 3;
    private static final int CookingSuspended = 4;
    private static final int SignallingCookingPeriodOver = 5;
    private static final int AssigningCookingPeriod = 6;
    private static final String[] stateNames = new String[] { "Non Existent", "Awaiting Cooking Request", "Ensuring Safe To Cook", "Cooking", "Cooking Suspended",
            "Signalling Cooking Period Over", "Assigning Cooking Period" };
    
    public OvenInstanceStateMachine() {
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, AssigningCookingPeriod },
            { StateEventMatrix.EVENT_IGNORED, EnsuringSafeToCook, AwaitingCookingRequest, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, Cooking, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, CookingSuspended, StateEventMatrix.EVENT_IGNORED, SignallingCookingPeriodOver, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, EnsuringSafeToCook, AwaitingCookingRequest, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, AwaitingCookingRequest, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, AwaitingCookingRequest, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, EnsuringSafeToCook, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED }
        }) {
            @Override
            public String getStateName( int state ) {
                return stateNames[state];
            }
        };
    }

    @Override
    public void stateActivity(int stateNum, Event e) throws XtumlException {
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
    
    private void stateAwaitingCookingRequest( Event e ) throws XtumlException {
        Oven self = (Oven)getInstance();
        // assign self.remaining_cooking_time = 0;
        self.setM_remaining_cooking_time( 0 );
        // select one beeper related by self->MO_B[R3];
        Beeper beeper = self.selectOneMO_BOnR3();
        // generate MO_B4:'stop_beeping' to beeper;
        new StopBeeping().generateTo( beeper );
    }

    private void stateEnsuringSafeToCook( Event e ) throws XtumlException {
        Oven self = (Oven)getInstance();
        // if (self.remaining_cooking_time != 0)
        if ( self.getM_remaining_cooking_time() != 0 ) {
            // select one door related by self->MO_D[R4];
            Door door = self.selectOneMO_DOnR4();
            // if (door.is_secure == true)
            if ( door.getM_is_secure() == true ) {
                // generate MO_O7:'oven_safe'() to self;
                new OvenSafe().generateToSelf( self );
            // end if;
            }
        // end if;
        }
    }

    private void stateCooking( Event e ) throws XtumlException {
        Oven self = (Oven)getInstance();
        // create event instance cooking_over of MO_O5:'cooking_period_over'() to self;
        Event cooking_over = new CookingPeriodOver( self, true );
        // self.oven_timer = TIM::timer_start(microseconds:self.remaining_cooking_time, event_inst:cooking_over);
        self.setM_oven_timer( TIM.timer_start( cooking_over, self.getM_remaining_cooking_time() ) );
        // select one light related by self->MO_IL[R2];
        InternalLight light = self.selectOneMO_ILOnR2();
        // generate MO_IL1:'switch_on' to light;
        new SwitchOn().generateTo( light );
        // select one turntable related by self->MO_TRN[R5];
        Turntable turntable  = self.selectOneMO_TRNOnR5();
        // generate MO_TRN1:'spin' to turntable;
        new Spin().generateTo( turntable );
        // select one tube related by self->MO_MT[R1];
        MagnetronTube tube = self.selectOneMO_MTOnR1();
        // generate MO_MT3:'power_on' to tube;
        new PowerOn().generateTo( tube );
    }

    private void stateCookingSuspended( Event e ) throws XtumlException {
        Oven self = (Oven)getInstance();
        // assign self.remaining_cooking_time = TIM::timer_remaining_time(timer_inst_ref:self.oven_timer);
        self.setM_remaining_cooking_time( TIM.timer_remaining_time( self.getM_oven_timer() ) );
        // cancelled = TIM::timer_cancel(timer_inst_ref:self.oven_timer);
        TIM.timer_cancel( self.getM_oven_timer() );
        // select one light related by self->MO_IL[R2];
        InternalLight light = self.selectOneMO_ILOnR2();
        // generate MO_IL2:'switch_off' to light;
        new SwitchOff().generateTo( light );
        // select one turntable related by self->MO_TRN[R5];
        Turntable turntable = self.selectOneMO_TRNOnR5();
        // generate MO_TRN2:'stop' to turntable;
        new Stop().generateTo( turntable );
        // select one tube related by self->MO_MT[R1];
        MagnetronTube tube = self.selectOneMO_MTOnR1();
        // generate MO_MT4:'power_off' to tube;
        new PowerOff().generateTo( tube );
    }

    private void stateSignallingCookingPeriodOver( Event e ) throws XtumlException {
        Oven self = (Oven)getInstance();
        // select one beeper related by self->MO_B[R3];
        Beeper beeper = self.selectOneMO_BOnR3();
        // generate MO_B1:'start_beeping' to beeper;
        new StartBeeping().generateTo( beeper );
        // select one light related by self->MO_IL[R2];
        InternalLight light = self.selectOneMO_ILOnR2();
        // generate MO_IL2:'switch_off' to light;
        new SwitchOff().generateTo( light );
        // select one turntable related by self->MO_TRN[R5];
        Turntable turntable = self.selectOneMO_TRNOnR5();
        // generate MO_TRN2:'stop' to turntable;
        new Stop().generateTo( turntable );
        // select one tube related by self->MO_MT[R1];
        MagnetronTube tube = self.selectOneMO_MTOnR1();
        // generate MO_MT4:'power_off' to tube;
        new PowerOff().generateTo( tube );
    }

    private void stateAssigningCookingPeriod( Event e ) throws XtumlException {
        Oven self = (Oven)getInstance();
        int period = (int)e.getData( "period" );
        // assign self.remaining_cooking_time = rcvd_evt.period;
        self.setM_remaining_cooking_time( period );
    }

}
