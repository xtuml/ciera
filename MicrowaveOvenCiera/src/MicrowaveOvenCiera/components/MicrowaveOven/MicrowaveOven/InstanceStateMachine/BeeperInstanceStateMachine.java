package MicrowaveOvenCiera.components.MicrowaveOven.MicrowaveOven.InstanceStateMachine;

import MicrowaveOvenCiera.components.MicrowaveOven.MicrowaveOven.Beeper;
import MicrowaveOvenCiera.components.MicrowaveOven.MicrowaveOven.Oven;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;
import ciera.statemachine.exceptions.StateMachineException;
import ciera.util.Timer;
import ciera.util.ees.TIM;

public class BeeperInstanceStateMachine extends InstanceStateMachine {
    
    private static final int AwaitingBeeperRequest = 1;
    private static final int Beeping = 2;
    
    public BeeperInstanceStateMachine( Beeper beeper ) {
        instance = beeper;
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { Beeping, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, Beeping, AwaitingBeeperRequest, AwaitingBeeperRequest }
        });
    }

    @Override
    protected void stateActivity(int stateNum, Event e) throws StateMachineException, EmptyInstanceException {
        if ( stateNum == AwaitingBeeperRequest ) {
            stateAwaitingBeeperRequest( e );
        }
        else if ( stateNum == AwaitingBeeperRequest ) {
            stateBeeping( e );
        }
        else throw new StateMachineException( "State does not exist. " );
    }
    
    private void stateAwaitingBeeperRequest( Event e ) throws EmptyInstanceException {
        Beeper self = (Beeper)e.getTarget();
        // assign self.beep_count = 0;
        self.setM_beep_count( 0 );
        // cancelled_timer = TIM::timer_cancel(timer_inst_ref:self.beeper_timer);
        Timer cancelled_timer = TIM.timer_cancel(self.getM_beeper_timer());
    }

    private void stateBeeping( Event e ) {
        Beeper self = (Beeper)e.getTarget();
        // if (self.beep_count == 0) // beeper yet to begin
        if ( self.getM_beep_count() == 0 ) {
            // create event instance delay_over of MO_B2:'beep_delay_over' to self;
            Event delay_over = new BeepDelayOver( self, true );
            // assign self.beeper_delay_over = delay_over;
            self.setM_beeper_delay_over(delay_over);
            // assign self.beeper_timer = TIM::timer_start(microseconds:100000,event_inst:self.beeper_delay_over);
            self.setM_beeper_timer( TIM.timer_start( self.getM_beeper_delay_over(), 100000 ) );
        }
        // elif (self.beep_count == 4) // last beep 
        else if ( self.getM_beep_count() == 4 ) {
            // generate MO_B3:'beeping_stopped' to self;
            self.generateTo( new BeepingStopped( self, true ) );
            // select one oven related by self->MO_O[R3];
            Oven oven = self.selectOneMO_OOnR3();
            // generate MO_O6:'beeping_over' to oven;
            oven.generateTo( new BeepingOver( oven, false ) );
        }
        // else
        else {
           // assign self.beeper_timer = TIM::timer_start(microseconds:100000,event_inst:self.beeper_delay_over);
           self.setM_beeper_timer( TIM.timer_start( self.getM_beeper_delay_over(), 100000 ) );
        }
        // end if;
        // assign self.beep_count = self.beep_count + 1;
        self.setM_beep_count( self.getM_beep_count() + 1 );
    }

}
