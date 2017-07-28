package microwaveovenciera.components.microwaveoven.microwaveoven.beeper.instancestatemachine;

import microwaveovenciera.components.microwaveoven.externalentities.TIM;
import microwaveovenciera.components.microwaveoven.microwaveoven.Beeper;
import microwaveovenciera.components.microwaveoven.microwaveoven.Oven;
import microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine.BeepingOver;
import ciera.exceptions.StateMachineException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;

public class BeeperInstanceStateMachine extends InstanceStateMachine {
    
    // states
    private static final int AwaitingBeeperRequest = 1;
    private static final int Beeping = 2;
    private static final String[] stateNames = new String[] { "Non Existent", "Awaiting Beeper Request", "Beeping" };
    
    public BeeperInstanceStateMachine() {
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { Beeping, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, Beeping, AwaitingBeeperRequest, AwaitingBeeperRequest }
        }) {
            @Override
            public String getStateName( int state ) {
                return stateNames[state];
            }
        };
    }

    @Override
    public void stateActivity(int stateNum, Event e) throws XtumlException {
        if ( stateNum == AwaitingBeeperRequest ) {
            stateAwaitingBeeperRequest( e );
        }
        else if ( stateNum == AwaitingBeeperRequest ) {
            stateBeeping( e );
        }
        else throw new StateMachineException( "State does not exist. " );
    }
    
    private void stateAwaitingBeeperRequest( Event e ) throws XtumlException {
        Beeper self = (Beeper)getInstance();
        // assign self.beep_count = 0;
        self.setM_beep_count( 0 );
        // cancelled_timer = TIM::timer_cancel(timer_inst_ref:self.beeper_timer);
        TIM.timer_cancel( self.getM_beeper_timer() );
    }

    private void stateBeeping( Event e ) throws XtumlException {
        Beeper self = (Beeper)getInstance();
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
            new BeepingStopped().generateToSelf( self );
            // select one oven related by self->MO_O[R3];
            Oven oven = self.selectOneMO_OOnR3();
            // generate MO_O6:'beeping_over' to oven;
            new BeepingOver().generateTo( oven );
        }
        // else
        else {
           // assign self.beeper_timer = TIM::timer_start(microseconds:100000,event_inst:self.beeper_delay_over);
           self.setM_beeper_timer( TIM.timer_start( self.getM_beeper_delay_over(), 100000 ) );
        // end if;
        }
        // assign self.beep_count = self.beep_count + 1;
        self.setM_beep_count( self.getM_beep_count() + 1 );
    }

}
