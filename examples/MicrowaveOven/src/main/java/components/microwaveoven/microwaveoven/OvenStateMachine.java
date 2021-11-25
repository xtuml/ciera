package components.microwaveoven.microwaveoven;

import components.MicrowaveOven;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.domain.InstanceStateMachine;
import io.ciera.runtime.domain.TransitionRule;
import io.ciera.runtime.exceptions.TransitionException;
import io.ciera.runtime.types.Duration;

public class OvenStateMachine extends InstanceStateMachine {

    public static enum State {
        NON_EXISTENT, AWAITING_COOKING_REQUEST, ASSIGNING_COOKING_PERIOD, COOKING, COOKING_SUSPENDED,
        ENSURING_SAFE_TO_COOK, SIGNALLING_COOKING_PERIOD_OVER;
    }

    private static enum Events {
        OVEN_SAFE, COOKING_PERIOD, COOKING_PERIOD_OVER, INITIALISE, BEEPING_OVER, CANCEL_COOKING, OVEN_INITIALISED,
        START_COOKING;
    }

    public OvenStateMachine(MicrowaveOven domain, Enum<?> initialState, Oven self) {
        super(domain, initialState, self);
    }

    private void Assigning_Cooking_Period_entry_action(final int p_period) {
        self().setRemaining_cooking_time(p_period);
    }

    private void Awaiting_Cooking_Request_entry_action() {
        self().setRemaining_cooking_time(0);
        Beeper beeper = self().R3_features_Beeper();
        beeper.getContext().generateEvent(BeeperStateMachine.stop_beeping.class, beeper);
    }

    private void Cooking_entry_action() {
        self().setOven_timer(self().getContext().scheduleEvent(OvenStateMachine.cooking_period_over.class, self(),
                new Duration(self().getRemaining_cooking_time() * 1000l)));
        InternalLight light = self().R2_is_illuminated_by_InternalLight();
        light.getContext().generateEvent(InternalLightStateMachine.switch_on.class, light);
        Turntable turntable = self().R5_has_Turntable();
        turntable.getContext().generateEvent(TurntableStateMachine.spin.class, turntable);
        MagnetronTube tube = self().R1_houses_MagnetronTube();
        tube.getContext().generateEvent(MagnetronTubeStateMachine.power_on.class, tube);
    }

    private void Cooking_Suspended_entry_action() {
        self().setRemaining_cooking_time((int) (self().getOven_timer().remainingTime() / 1000l));
        self().getOven_timer().cancel();
        InternalLight light = self().R2_is_illuminated_by_InternalLight();
        light.getContext().generateEvent(InternalLightStateMachine.switch_off.class, light);
        Turntable turntable = self().R5_has_Turntable();
        turntable.getContext().generateEvent(TurntableStateMachine.stop.class, turntable);
        MagnetronTube tube = self().R1_houses_MagnetronTube();
        tube.getContext().generateEvent(MagnetronTubeStateMachine.power_off.class, tube);
    }

    private void Ensuring_Safe_to_Cook_entry_action() {
        if (self().getRemaining_cooking_time() != 0) {
            Door door = self().R4_is_accessed_via_Door();
            if (door.getIs_secure() == true) {
                self().getContext().generateEventToSelf(OvenStateMachine.oven_safe.class, self());
            }
        }
    }

    private void Signalling_Cooking_Period_Over_entry_action() {
        Beeper beeper = self().R3_features_Beeper();
        beeper.getContext().generateEvent(BeeperStateMachine.start_beeping.class, beeper);
        InternalLight light = self().R2_is_illuminated_by_InternalLight();
        light.getContext().generateEvent(InternalLightStateMachine.switch_off.class, light);
        Turntable turntable = self().R5_has_Turntable();
        turntable.getContext().generateEvent(TurntableStateMachine.stop.class, turntable);
        MagnetronTube tube = self().R1_houses_MagnetronTube();
        tube.getContext().generateEvent(MagnetronTubeStateMachine.power_off.class, tube);
    }

    @Override
    public TransitionRule getTransition(Enum<?> currentState, Event event) {
        State state = (State) currentState;
        Events eventId = (Events) event.getEventId();
        switch (state) {
        case NON_EXISTENT:
            switch (eventId) {
            case OVEN_SAFE:
                return cannotHappen(currentState, event);
            case COOKING_PERIOD:
                return cannotHappen(currentState, event);
            case COOKING_PERIOD_OVER:
                return cannotHappen(currentState, event);
            case INITIALISE:
                return cannotHappen(currentState, event);
            case BEEPING_OVER:
                return cannotHappen(currentState, event);
            case CANCEL_COOKING:
                return cannotHappen(currentState, event);
            case OVEN_INITIALISED:
                return cannotHappen(currentState, event);
            case START_COOKING:
                return cannotHappen(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case AWAITING_COOKING_REQUEST:
            switch (eventId) {
            case OVEN_SAFE:
                return ignore(currentState, event);
            case COOKING_PERIOD:
                return () -> {
                    Assigning_Cooking_Period_entry_action((int) event.get(0));
                    return State.ASSIGNING_COOKING_PERIOD;
                };
            case COOKING_PERIOD_OVER:
                return ignore(currentState, event);
            case INITIALISE:
                return ignore(currentState, event);
            case BEEPING_OVER:
                return ignore(currentState, event);
            case CANCEL_COOKING:
                return ignore(currentState, event);
            case OVEN_INITIALISED:
                return ignore(currentState, event);
            case START_COOKING:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case ASSIGNING_COOKING_PERIOD:
            switch (eventId) {
            case OVEN_SAFE:
                return ignore(currentState, event);
            case COOKING_PERIOD:
                return ignore(currentState, event);
            case COOKING_PERIOD_OVER:
                return ignore(currentState, event);
            case INITIALISE:
                return ignore(currentState, event);
            case BEEPING_OVER:
                return ignore(currentState, event);
            case CANCEL_COOKING:
                return ignore(currentState, event);
            case OVEN_INITIALISED:
                return ignore(currentState, event);
            case START_COOKING:
                return () -> {
                    Ensuring_Safe_to_Cook_entry_action();
                    return State.ENSURING_SAFE_TO_COOK;
                };
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case COOKING:
            switch (eventId) {
            case OVEN_SAFE:
                return ignore(currentState, event);
            case COOKING_PERIOD:
                return ignore(currentState, event);
            case COOKING_PERIOD_OVER:
                return () -> {
                    Signalling_Cooking_Period_Over_entry_action();
                    return State.SIGNALLING_COOKING_PERIOD_OVER;
                };
            case INITIALISE:
                return ignore(currentState, event);
            case BEEPING_OVER:
                return ignore(currentState, event);
            case CANCEL_COOKING:
                return () -> {
                    Cooking_Suspended_entry_action();
                    return State.COOKING_SUSPENDED;
                };
            case OVEN_INITIALISED:
                return ignore(currentState, event);
            case START_COOKING:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case COOKING_SUSPENDED:
            switch (eventId) {
            case OVEN_SAFE:
                return ignore(currentState, event);
            case COOKING_PERIOD:
                return ignore(currentState, event);
            case COOKING_PERIOD_OVER:
                return ignore(currentState, event);
            case INITIALISE:
                return ignore(currentState, event);
            case BEEPING_OVER:
                return ignore(currentState, event);
            case CANCEL_COOKING:
                return () -> {
                    Awaiting_Cooking_Request_entry_action();
                    return State.AWAITING_COOKING_REQUEST;
                };
            case OVEN_INITIALISED:
                return ignore(currentState, event);
            case START_COOKING:
                return () -> {
                    Ensuring_Safe_to_Cook_entry_action();
                    return State.ENSURING_SAFE_TO_COOK;
                };
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case ENSURING_SAFE_TO_COOK:
            switch (eventId) {
            case OVEN_SAFE:
                return () -> {
                    Cooking_entry_action();
                    return State.COOKING;
                };
            case COOKING_PERIOD:
                return ignore(currentState, event);
            case COOKING_PERIOD_OVER:
                return ignore(currentState, event);
            case INITIALISE:
                return ignore(currentState, event);
            case BEEPING_OVER:
                return ignore(currentState, event);
            case CANCEL_COOKING:
                return () -> {
                    Awaiting_Cooking_Request_entry_action();
                    return State.AWAITING_COOKING_REQUEST;
                };
            case OVEN_INITIALISED:
                return ignore(currentState, event);
            case START_COOKING:
                return () -> {
                    Ensuring_Safe_to_Cook_entry_action();
                    return State.ENSURING_SAFE_TO_COOK;
                };
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case SIGNALLING_COOKING_PERIOD_OVER:
            switch (eventId) {
            case OVEN_SAFE:
                return ignore(currentState, event);
            case COOKING_PERIOD:
                return ignore(currentState, event);
            case COOKING_PERIOD_OVER:
                return ignore(currentState, event);
            case INITIALISE:
                return ignore(currentState, event);
            case BEEPING_OVER:
                return () -> {
                    Awaiting_Cooking_Request_entry_action();
                    return State.AWAITING_COOKING_REQUEST;
                };
            case CANCEL_COOKING:
                return () -> {
                    Awaiting_Cooking_Request_entry_action();
                    return State.AWAITING_COOKING_REQUEST;
                };
            case OVEN_INITIALISED:
                return ignore(currentState, event);
            case START_COOKING:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        default:
            throw new TransitionException(currentState, event, "Unknown state");
        }
    }

    // events
    public static class oven_safe extends Event {
        public oven_safe(Object... data) {
            super(Events.OVEN_SAFE, data);
        }
    }

    public static class cooking_period extends Event {
        public cooking_period(Object... data) {
            super(Events.COOKING_PERIOD, data);
        }
    }

    public static class cooking_period_over extends Event {
        public cooking_period_over(Object... data) {
            super(Events.COOKING_PERIOD_OVER, data);
        }
    }

    public static class initialise extends Event {
        public initialise(Object... data) {
            super(Events.INITIALISE, data);
        }
    }

    public static class beeping_over extends Event {
        public beeping_over(Object... data) {
            super(Events.BEEPING_OVER, data);
        }
    }

    public static class cancel_cooking extends Event {
        public cancel_cooking(Object... data) {
            super(Events.CANCEL_COOKING, data);
        }
    }

    public static class oven_initialised extends Event {
        public oven_initialised(Object... data) {
            super(Events.OVEN_INITIALISED, data);
        }
    }

    public static class start_cooking extends Event {
        public start_cooking(Object... data) {
            super(Events.START_COOKING, data);
        }
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public Oven self() {
        return (Oven) super.self();
    }

}
