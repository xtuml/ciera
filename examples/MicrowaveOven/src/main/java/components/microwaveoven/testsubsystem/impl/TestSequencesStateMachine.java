package components.microwaveoven.testsubsystem.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Door;
import components.microwaveoven.microwaveoven.MagnetronTube;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.impl.DoorImpl;
import components.microwaveoven.microwaveoven.impl.MagnetronTubeImpl;
import components.microwaveoven.microwaveoven.impl.OvenImpl;
import components.microwaveoven.testsubsystem.TestSequences;
import components.microwaveoven.testsubsystem.impl.TestSequencesImpl;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.statemachine.ITransition;
import io.ciera.runtime.summit.statemachine.StateMachine;


public class TestSequencesStateMachine extends StateMachine<TestSequences,MicrowaveOven> {

    public static final int Awaiting_Test_Sequence_Initiation = 0;
    public static final int Cooking_Complete = 1;
    public static final int Performing_Test_Sequence_1 = 2;
    public static final int Performing_Test_Sequence_2 = 3;


    private TestSequences self;

    public TestSequencesStateMachine(TestSequences self, MicrowaveOven context) {
        this(self, context, Awaiting_Test_Sequence_Initiation);
    }

    public TestSequencesStateMachine(TestSequences self, MicrowaveOven context, int initialState) {
        super(context, initialState);
        this.self = self;
    }

    private void Awaiting_Test_Sequence_Initiation_entry_action() throws XtumlException {
        context().generate(new TestSequencesImpl.perform_test_seq_1(getRunContext(), context().getId()).toSelf(self()));
    }

    private void Cooking_Complete_entry_action() throws XtumlException {
        context().SQL().serialize_file( "out.txt" );
        context().ARCH().shutdown();
    }

    private void Performing_Test_Sequence_1_entry_action() throws XtumlException {
        Door door = context().Door_instances().any();
        if ( !door.isEmpty() ) {
            EventHandle release_door = new DoorImpl.release(getRunContext(), context().getId()).to(door);
            context().TIM().timer_start( release_door, 2000000 );
        }
        MagnetronTube tube = context().MagnetronTube_instances().any();
        if ( !tube.isEmpty() ) {
            EventHandle lower_power = new MagnetronTubeImpl.decrease_power(getRunContext(), context().getId()).to(tube);
            context().TIM().timer_start( lower_power, 3000000 );
        }
        door = context().Door_instances().any();
        if ( !door.isEmpty() ) {
            EventHandle close_door = new DoorImpl.close(getRunContext(), context().getId()).to(door);
            context().TIM().timer_start( close_door, 4000000 );
        }
        Oven oven = context().Oven_instances().any();
        if ( !oven.isEmpty() ) {
            EventHandle cooking_time = new OvenImpl.cooking_period(getRunContext(), context().getId(),  10000000 ).to(oven);
            context().TIM().timer_start( cooking_time, 5000000 );
            EventHandle start = new OvenImpl.start_cooking(getRunContext(), context().getId()).to(oven);
            context().TIM().timer_start( start, 5000001 );
        }
        door = context().Door_instances().any();
        if ( !door.isEmpty() ) {
            EventHandle release_door = new DoorImpl.release(getRunContext(), context().getId()).to(door);
            context().TIM().timer_start( release_door, 15000000 );
        }
        EventHandle finished = new TestSequencesImpl.test_seq_complete(getRunContext(), context().getId()).toSelf(self());
        context().TIM().timer_start( finished, 30000000 );
    }

    private void Performing_Test_Sequence_2_entry_action() throws XtumlException {
        Door door = context().Door_instances().any();
        if ( !door.isEmpty() ) {
            EventHandle release_door = new DoorImpl.release(getRunContext(), context().getId()).to(door);
            context().TIM().timer_start( release_door, 2000000 );
        }
        door = context().Door_instances().any();
        if ( !door.isEmpty() ) {
            EventHandle close_door = new DoorImpl.close(getRunContext(), context().getId()).to(door);
            context().TIM().timer_start( close_door, 4000000 );
        }
        Oven oven = context().Oven_instances().any();
        if ( !oven.isEmpty() ) {
            EventHandle cooking_time = new OvenImpl.cooking_period(getRunContext(), context().getId(),  15000000 ).to(oven);
            context().TIM().timer_start( cooking_time, 5000000 );
            EventHandle start = new OvenImpl.start_cooking(getRunContext(), context().getId()).to(oven);
            context().TIM().timer_start( start, 5000001 );
        }
        door = context().Door_instances().any();
        if ( !door.isEmpty() ) {
            EventHandle release_door = new DoorImpl.release(getRunContext(), context().getId()).to(door);
            context().TIM().timer_start( release_door, 6000000 );
        }
        MagnetronTube tube = context().MagnetronTube_instances().any();
        if ( !tube.isEmpty() ) {
            EventHandle lower_power = new MagnetronTubeImpl.decrease_power(getRunContext(), context().getId()).to(tube);
            context().TIM().timer_start( lower_power, 3000000 );
            context().TIM().timer_start( lower_power, 3000001 );
        }
        door = context().Door_instances().any();
        if ( !door.isEmpty() ) {
            EventHandle close_door = new DoorImpl.close(getRunContext(), context().getId()).to(door);
            context().TIM().timer_start( close_door, 9000000 );
        }
        oven = context().Oven_instances().any();
        if ( !oven.isEmpty() ) {
            EventHandle restart = new OvenImpl.start_cooking(getRunContext(), context().getId()).to(oven);
            context().TIM().timer_start( restart, 10000000 );
        }
        EventHandle finished = new TestSequencesImpl.test_seq_complete(getRunContext(), context().getId()).toSelf(self());
        context().TIM().timer_start( finished, 30000000 );
    }



    private void Awaiting_Test_Sequence_Initiation_initialize_txn_to_Awaiting_Test_Sequence_Initiation_action() throws XtumlException {
    }

    private void Awaiting_Test_Sequence_Initiation_perform_test_seq_1_txn_to_Performing_Test_Sequence_1_action() throws XtumlException {
    }

    private void Awaiting_Test_Sequence_Initiation_perform_test_seq_2_txn_to_Performing_Test_Sequence_2_action() throws XtumlException {
    }

    private void Performing_Test_Sequence_1_test_seq_complete_txn_to_Cooking_Complete_action() throws XtumlException {
    }

    private void Performing_Test_Sequence_2_test_seq_complete_txn_to_Cooking_Complete_action() throws XtumlException {
    }



    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
            { (event) -> {Awaiting_Test_Sequence_Initiation_perform_test_seq_1_txn_to_Performing_Test_Sequence_1_action();Performing_Test_Sequence_1_entry_action();return Performing_Test_Sequence_1;},
              IGNORE,
              (event) -> {Awaiting_Test_Sequence_Initiation_perform_test_seq_2_txn_to_Performing_Test_Sequence_2_action();Performing_Test_Sequence_2_entry_action();return Performing_Test_Sequence_2;},
              (event) -> {Awaiting_Test_Sequence_Initiation_initialize_txn_to_Awaiting_Test_Sequence_Initiation_action();Awaiting_Test_Sequence_Initiation_entry_action();return Awaiting_Test_Sequence_Initiation;}
            },
            { IGNORE,
              IGNORE,
              IGNORE,
              IGNORE
            },
            { IGNORE,
              (event) -> {Performing_Test_Sequence_1_test_seq_complete_txn_to_Cooking_Complete_action();Cooking_Complete_entry_action();return Cooking_Complete;},
              IGNORE,
              IGNORE
            },
            { IGNORE,
              (event) -> {Performing_Test_Sequence_2_test_seq_complete_txn_to_Cooking_Complete_action();Cooking_Complete_entry_action();return Cooking_Complete;},
              IGNORE,
              IGNORE
            }
        };
    }

    @Override
    public TestSequences self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "TestSequences";
    }

}
