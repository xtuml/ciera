package components.microwaveoven.testsubsystem;

import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Door;
import components.microwaveoven.microwaveoven.DoorStateMachine;
import components.microwaveoven.microwaveoven.MagnetronTube;
import components.microwaveoven.microwaveoven.MagnetronTubeStateMachine;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.OvenStateMachine;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.domain.InstanceStateMachine;
import io.ciera.runtime.domain.TransitionRule;
import io.ciera.runtime.exceptions.TransitionException;
import io.ciera.runtime.types.Duration;

public class TestSequencesStateMachine extends InstanceStateMachine {

    public static enum States {
        NON_EXISTENT, AWAITING_TEST_SEQUENCE_INITIATION, COOKING_COMPLETE, PERFORMING_TEST_SEQUENCE_1,
        PERFORMING_TEST_SEQUENCE_2;

    }

    private static enum Events {
        PERFORM_TEST_SEQ_1, TEST_SEQ_COMPLETE, PERFORM_TEST_SEQ_2, INITIALIZE;
    }

    public TestSequencesStateMachine(MicrowaveOven domain, Enum<?> initialState, TestSequences self) {
        super(domain, initialState, self);
    }

    private void Awaiting_Test_Sequence_Initiation_entry_action() {
        self().getContext().generateEventToSelf(TestSequencesStateMachine.perform_test_seq_1.class, self());
    }

    private void Cooking_Complete_entry_action() {
        getDomain().ARCH.shutdown();
    }

    private void Performing_Test_Sequence_1_entry_action() {
        Door door = getDomain().Door_instances().any();
        if (!door.isEmpty()) {
            door.getContext().scheduleEvent(DoorStateMachine.release.class, door, new Duration(2000000000l));
        }
        MagnetronTube tube = getDomain().MagnetronTube_instances().any();
        if (!tube.isEmpty()) {
            tube.getContext().scheduleEvent(MagnetronTubeStateMachine.decrease_power.class, tube,
                    new Duration(3000000000l));
        }
        door = getDomain().Door_instances().any();
        if (!door.isEmpty()) {
            door.getContext().scheduleEvent(DoorStateMachine.close.class, door, new Duration(4000000000l));
        }
        Oven oven = getDomain().Oven_instances().any();
        if (!oven.isEmpty()) {
            oven.getContext().scheduleEvent(OvenStateMachine.cooking_period.class, oven, new Duration(5000000000l),
                    (int) (10000000000l));
            oven.getContext().scheduleEvent(OvenStateMachine.start_cooking.class, oven, new Duration(5000001000l));
        }
        door = getDomain().Door_instances().any();
        if (!door.isEmpty()) {
            door.getContext().scheduleEvent(DoorStateMachine.release.class, door, new Duration(15000000000l));
        }
        self().getContext().scheduleEvent(TestSequencesStateMachine.test_seq_complete.class, self(),
                new Duration(30000000000l));
    }

    private void Performing_Test_Sequence_2_entry_action() {
        Door door = getDomain().Door_instances().any();
        if (!door.isEmpty()) {
            door.getContext().scheduleEvent(DoorStateMachine.release.class, door, new Duration(2000000000l));
        }
        door = getDomain().Door_instances().any();
        if (!door.isEmpty()) {
            door.getContext().scheduleEvent(DoorStateMachine.close.class, door, new Duration(4000000000l));
        }
        Oven oven = getDomain().Oven_instances().any();
        if (!oven.isEmpty()) {
            oven.getContext().scheduleEvent(OvenStateMachine.cooking_period.class, oven, new Duration(5000000000l),
                    (int) (15000000000l));
            oven.getContext().scheduleEvent(OvenStateMachine.start_cooking.class, oven, new Duration(5000001000l));
        }
        door = getDomain().Door_instances().any();
        if (!door.isEmpty()) {
            door.getContext().scheduleEvent(DoorStateMachine.release.class, door, new Duration(6000000000l));
        }
        MagnetronTube tube = getDomain().MagnetronTube_instances().any();
        if (!tube.isEmpty()) {
            tube.getContext().scheduleEvent(MagnetronTubeStateMachine.decrease_power.class, tube,
                    new Duration(3000000000l));
            tube.getContext().scheduleEvent(MagnetronTubeStateMachine.decrease_power.class, tube,
                    new Duration(3000001000l));
        }
        door = getDomain().Door_instances().any();
        if (!door.isEmpty()) {
            door.getContext().scheduleEvent(DoorStateMachine.close.class, door, new Duration(9000000000l));
        }
        oven = getDomain().Oven_instances().any();
        if (!oven.isEmpty()) {
            oven.getContext().scheduleEvent(OvenStateMachine.start_cooking.class, oven, new Duration(10000000000l));
        }
        self().getContext().scheduleEvent(TestSequencesStateMachine.test_seq_complete.class, self(),
                new Duration(30000000000l));
    }

    @Override
    public TransitionRule getTransition(Enum<?> currentState, Event event) {
        States state = (States) currentState;
        Events eventId = (Events) event.getEventId();
        switch (state) {
        case NON_EXISTENT:
            switch (eventId) {
            case PERFORM_TEST_SEQ_1:
                return cannotHappen(currentState, event);
            case TEST_SEQ_COMPLETE:
                return cannotHappen(currentState, event);
            case PERFORM_TEST_SEQ_2:
                return cannotHappen(currentState, event);
            case INITIALIZE:
                return cannotHappen(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case AWAITING_TEST_SEQUENCE_INITIATION:
            switch (eventId) {
            case PERFORM_TEST_SEQ_1:
                return () -> {
                    Performing_Test_Sequence_1_entry_action();
                    return States.PERFORMING_TEST_SEQUENCE_1;
                };
            case TEST_SEQ_COMPLETE:
                return ignore(currentState, event);
            case PERFORM_TEST_SEQ_2:
                return () -> {
                    Performing_Test_Sequence_2_entry_action();
                    return States.PERFORMING_TEST_SEQUENCE_2;
                };
            case INITIALIZE:
                return () -> {
                    Awaiting_Test_Sequence_Initiation_entry_action();
                    return States.AWAITING_TEST_SEQUENCE_INITIATION;
                };
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case COOKING_COMPLETE:
            switch (eventId) {
            case PERFORM_TEST_SEQ_1:
                return ignore(currentState, event);
            case TEST_SEQ_COMPLETE:
                return ignore(currentState, event);
            case PERFORM_TEST_SEQ_2:
                return ignore(currentState, event);
            case INITIALIZE:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case PERFORMING_TEST_SEQUENCE_1:
            switch (eventId) {
            case PERFORM_TEST_SEQ_1:
                return ignore(currentState, event);
            case TEST_SEQ_COMPLETE:
                return () -> {
                    Cooking_Complete_entry_action();
                    return States.COOKING_COMPLETE;
                };
            case PERFORM_TEST_SEQ_2:
                return ignore(currentState, event);
            case INITIALIZE:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case PERFORMING_TEST_SEQUENCE_2:
            switch (eventId) {
            case PERFORM_TEST_SEQ_1:
                return ignore(currentState, event);
            case TEST_SEQ_COMPLETE:
                return () -> {
                    Cooking_Complete_entry_action();
                    return States.COOKING_COMPLETE;
                };
            case PERFORM_TEST_SEQ_2:
                return ignore(currentState, event);
            case INITIALIZE:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        default:
            throw new TransitionException(currentState, event, "Unknown state");
        }
    }

    // events
    public static class perform_test_seq_1 extends Event {
        public perform_test_seq_1(Object... data) {
            super(Events.PERFORM_TEST_SEQ_1, data);
        }
    }

    public static class test_seq_complete extends Event {
        public test_seq_complete(Object... data) {
            super(Events.TEST_SEQ_COMPLETE, data);
        }
    }

    public static class perform_test_seq_2 extends Event {
        public perform_test_seq_2(Object... data) {
            super(Events.PERFORM_TEST_SEQ_2, data);
        }
    }

    public static class initialize extends Event {
        public initialize(Object... data) {
            super(Events.INITIALIZE, data);
        }
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public TestSequences self() {
        return (TestSequences) super.self();
    }

}
