package microwaveovenciera.components.microwaveoven.testsubsystem.testsequences.instancestatemachine;

import microwaveovenciera.components.microwaveoven.MicrowaveOven;
import microwaveovenciera.components.microwaveoven.microwaveoven.Door;
import microwaveovenciera.components.microwaveoven.microwaveoven.MagnetronTube;
import microwaveovenciera.components.microwaveoven.microwaveoven.Oven;
import microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine.Close;
import microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine.Release;
import microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine.DecreasePower;
import microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine.CookingPeriod;
import microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine.StartCooking;
import microwaveovenciera.components.microwaveoven.testsubsystem.TestSequences;
import ciera.classes.EmptyInstance;
import ciera.exceptions.StateMachineException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;
import ciera.statemachine.InstanceStateMachine;
import ciera.statemachine.StateEventMatrix;
import ciera.util.Timer;
import ciera.util.ees.ARCH;
import ciera.util.ees.TIM;

public class TestSequencesInstanceStateMachine extends InstanceStateMachine {
    
    // states
    private static final int AwaitingTestSequenceInitiation = 1;
    private static final int CookingComplete = 2;
    private static final int PerformingTestSequence1 = 3;
    private static final int PerformingTestSequence2 = 4;
    
    public TestSequencesInstanceStateMachine() {
        sem = new StateEventMatrix( new int[][]{
            { StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN, StateEventMatrix.CANNOT_HAPPEN },
            { PerformingTestSequence1, PerformingTestSequence1, AwaitingTestSequenceInitiation, StateEventMatrix.EVENT_IGNORED },
            { StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, CookingComplete },
            { StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, CookingComplete },
            { StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED, StateEventMatrix.EVENT_IGNORED }
        });
    }

    @Override
    protected void stateActivity(int stateNum, Event e) throws XtumlException {
        if ( stateNum == AwaitingTestSequenceInitiation ) {
            stateAwaitingTestSequenceInitiation( e );
        }
        else if ( stateNum == CookingComplete ) {
            stateCookingComplete( e );
        }
        else if ( stateNum == PerformingTestSequence1 ) {
            statePerformingTestSequence1( e );
        }
        else if ( stateNum == PerformingTestSequence2 ) {
            statePerformingTestSequence2( e );
        }
        else throw new StateMachineException( "State does not exist. " );
    }

    private void stateAwaitingTestSequenceInitiation( Event e ) throws XtumlException {
        TestSequences self = (TestSequences)getInstance();
        // Automatically initiate test sequence 1.
        // generate MO_TS2:'perform_test_seq_1' to self;
        self.generateToSelf( new PerformTestSeq1() );
    }

    private void stateCookingComplete( Event e ) {
        // For code generation: terminate system once complete
        // Bridge ARCH::shutdown();
        ARCH.shutdown();
    }

    @SuppressWarnings("unused")
    private void statePerformingTestSequence1( Event e ) {
        TestSequences self = (TestSequences)getInstance();
        MicrowaveOven context = (MicrowaveOven)self.getContext();
        // Step 1. At T+2000000us, open the oven door to insert dish
        // select any door from instances of MO_D;
        Door door = context.selectAnyMO_DFromInstances();
        // if (not_empty door)
        if ( !( door instanceof EmptyInstance ) ) {
            // create event instance release_door of MO_D1:'release' to door;
            Event release_door = new Release( door, false );
            // step1_timer=TIM::timer_start(microseconds:2000000,event_inst:release_door);
            Timer step1_timer = TIM.timer_start( release_door, 2000000 );
        // end if;
        }
        // Step 2. At T+3000000us, lower the output power setting to Med_High
        // select any tube from instances of MO_MT;
        MagnetronTube tube = context.selectAnyMO_MTFromInstances();
        // if (not_empty tube)
        if ( !( tube instanceof EmptyInstance ) ) {
            // create event instance lower_power of MO_MT2:'decrease_power' to tube;
            Event lower_power = new DecreasePower( tube, false );
            // step2_timer =TIM::timer_start(microseconds:3000000,event_inst:lower_power);
            Timer step2_timer = TIM.timer_start( lower_power, 3000000 );
        // end if;
        }
        // Step 3. At T+4000000us, close oven door
        // select any door from instances of MO_D;
        door = context.selectAnyMO_DFromInstances();
        // if (not_empty door)
        if ( !( door instanceof EmptyInstance ) ) {
            // create event instance close_door of MO_D2:'close' to door;
            Event close_door = new Close( door, false );
            // step3_timer =TIM::timer_start(microseconds:4000000,event_inst:close_door);
            Timer step3_timer = TIM.timer_start( close_door, 4000000 );
        // end if;
        }
        // Step 4. At T+5000000us, assign cooking period of 10 seconds and start cooking
        // select any oven from instances of MO_O;
        Oven oven = context.selectAnyMO_OFromInstances();
        // if (not_empty oven)
        if ( !( oven instanceof EmptyInstance ) ) {
            // create event instance cooking_time of MO_O8:'cooking_period'(period:10000000) to oven;
            Event cooking_time = new CookingPeriod( oven, false, 10000000 );
            // step4a_timer =TIM::timer_start(microseconds:5000000,event_inst:cooking_time);
            Timer step4a_timer = TIM.timer_start( cooking_time, 5000000 );
            // create event instance start of MO_O3:'start_cooking' to oven;
            Event start = new StartCooking( oven, false );
            // step4b_timer =TIM::timer_start(microseconds:5000001,event_inst:start);
            Timer step4b_timer = TIM.timer_start( start, 5000001 );
        // end if;
        }
        // Step 5. At T+15secs, open the oven door to remove cooked dish
        // select any door from instances of MO_D;
        door = context.selectAnyMO_DFromInstances();
        // if (not_empty door)
        if ( !( door instanceof EmptyInstance ) ) {
            // create event instance release_door of MO_D1:'release' to door;
            Event release_door = new Release( door, false );
            // step5_timer =TIM::timer_start(microseconds:15000000,event_inst:release_door);
            Timer step5_timer = TIM.timer_start( release_door, 15000000 );
        // end if;
        }
        // For codegen: Testing complete.  After 30 seconds, terminate the system
        // otherwise, executable will not terminate.
        // create event instance finished of MO_TS4:'test_seq_complete' to self;
        Event finished = new TestSeqComplete( self, true );
        // terminate_timer=TIM::timer_start(microseconds:30000000,event_inst:finished);
        Timer terminate_timer = TIM.timer_start( finished, 30000000 );
    }

    @SuppressWarnings("unused")
    private void statePerformingTestSequence2( Event e ) {
        TestSequences self = (TestSequences)getInstance();
        MicrowaveOven context = (MicrowaveOven)self.getContext();
        // Step 1. At T+2000000us, open the oven door to insert dish
        // select any door from instances of MO_D;
        Door door = context.selectAnyMO_DFromInstances();
        // if (not_empty door)
        if ( !( door instanceof EmptyInstance ) ) {
            // create event instance release_door of MO_D1:'release' to door;
            Event release_door = new Release( door, false );
            // step1_timer =TIM::timer_start(microseconds:2000000,event_inst:release_door);
            Timer step1_timer = TIM.timer_start( release_door, 2000000 );
        // end if;
        }
        // Step 2. At T+4000000us, close oven door
        // select any door from instances of MO_D;
        door = context.selectAnyMO_DFromInstances();
        // if (not_empty door)
        if ( !( door instanceof EmptyInstance ) ) {
            // create event instance close_door of MO_D2:'close' to door;
            Event close_door = new Close( door, false );
            // step2_timer =TIM::timer_start(microseconds:4000000,event_inst:close_door);
            Timer step2_timer = TIM.timer_start( close_door, 4000000 );
        // end if;
        }
        // Step 3. At T+5000000us, assign cooking period of 15seconds and start cooking
        // select any oven from instances of MO_O;
        Oven oven = context.selectAnyMO_OFromInstances();
        // if (not_empty oven)
        if ( !( oven instanceof EmptyInstance ) ) {
            // create event instance cooking_time of MO_O8:'cooking_period'(period:15000000) to oven;
            Event cooking_time = new CookingPeriod( oven, false, 15000000 );
            // step3a_timer =TIM::timer_start(microseconds:5000000,event_inst:cooking_time);
            Timer step3a_timer = TIM.timer_start( cooking_time, 5000000 );
            // create event instance start of MO_O3:'start_cooking' to oven;
            Event start = new StartCooking( oven, false );
            // step3b_timer =TIM::timer_start(microseconds:5000001,event_inst:start);
            Timer step3b_timer = TIM.timer_start( start, 5000001 );
        // end if;
        }
        // Step 4. At T+6000000us, open the oven door to inspect dish
        // select any door from instances of MO_D;
        door = context.selectAnyMO_DFromInstances();
        // if (not_empty door)
        if ( !( door instanceof EmptyInstance ) ) {
            // create event instance release_door of MO_D1:'release' to door;
            Event release_door = new Release( door, false );
            // step4_timer =TIM::timer_start(microseconds:6000000,event_inst:release_door);
            Timer step4_timer = TIM.timer_start( release_door, 6000000 );
        // end if;
        }
        // Step 5. At T+7000000us, lower the output power setting to Medium
        // select any tube from instances of MO_MT;
        MagnetronTube tube = context.selectAnyMO_MTFromInstances();
        // if (not_empty tube)
        if ( !( tube instanceof EmptyInstance ) ) {
            // create event instance lower_power of MO_MT2:'decrease_power' to tube;
            Event lower_power = new DecreasePower( tube, false );
            // step5a_timer =TIM::timer_start(microseconds:3000000,event_inst:lower_power);
            Timer step5a_timer = TIM.timer_start( lower_power, 3000000 );
            // step5b_timer = TIM::timer_start(microseconds:3000001,event_inst:lower_power);
            Timer step5b_timer = TIM.timer_start( lower_power, 3000001 );
        // end if;
        }
        // Step 6. At T+9000000us, close oven door
        // select any door from instances of MO_D;
        door = context.selectAnyMO_DFromInstances();
        // if (not_empty door)
        if ( !( door instanceof EmptyInstance ) ) {
            // create event instance close_door of MO_D2:'close' to door;
            Event close_door = new Close( door, false );
            // step6_timer =TIM::timer_start(microseconds:9000000,event_inst:close_door);
            Timer step6_timer = TIM.timer_start( close_door, 9000000 );
        // end if;
        }
        // Step 7. At T+10000000us, resume cooking
        // select any oven from instances of MO_O;
        oven = context.selectAnyMO_OFromInstances();
        // if (not_empty oven)
        if ( !( oven instanceof EmptyInstance ) ) {
            // create event instance restart of MO_O3:'start_cooking' to oven;
            Event start = new StartCooking( oven, false );
            // step7_timer =TIM::timer_start(microseconds:10000000,event_inst:restart);
            Timer step7_timer = TIM.timer_start( start, 10000000 );
        // end if;
        }
        // For codegen: Testing complete.  After 30 seconds, terminate the system
        // otherwise, executable will not terminate.
        // create event instance finished of MO_TS4:'test_seq_complete' to self;
        Event finished = new TestSeqComplete( self, true );
        // terminate_timer=TIM::timer_start(microseconds:30000000,event_inst:finished);
        Timer terminate_timer = TIM.timer_start( finished, 30000000 );
    }

}
