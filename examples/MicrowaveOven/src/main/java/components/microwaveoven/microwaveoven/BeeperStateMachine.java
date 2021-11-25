package components.microwaveoven.microwaveoven;

import components.MicrowaveOven;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.domain.InstanceStateMachine;
import io.ciera.runtime.domain.TransitionRule;
import io.ciera.runtime.exceptions.TransitionException;
import io.ciera.runtime.types.Duration;

public class BeeperStateMachine extends InstanceStateMachine {

    public static enum States {
        NON_EXISTENT, AWAITING_BEEPER_REQUEST, BEEPING;
    }

    private static enum Events {
        START_BEEPING, BEEP_DELAY_OVER, STOP_BEEPING, BEEPING_STOPPED;
    }

    public BeeperStateMachine(MicrowaveOven domain, Enum<?> initialState, Beeper self) {
        super(domain, initialState, self);
    }

    private void Awaiting_Beeper_Request_entry_action() {
        self().setBeep_count(0);
        self().getBeeper_timer().cancel();
    }

    private void Beeping_entry_action() {
        if (self().getBeep_count() == 0) {
            self().setBeeper_timer(self().getContext().scheduleEvent(BeeperStateMachine.beep_delay_over.class, self(),
                    new Duration(100000000l)));
        } else if (self().getBeep_count() == 4) {
            self().getContext().generateEventToSelf(BeeperStateMachine.beeping_stopped.class, self());
            Oven oven = self().R3_is_located_in_Oven();
            oven.getContext().generateEvent(OvenStateMachine.beeping_over.class, oven);
        } else {
            self().setBeeper_timer(self().getContext().scheduleEvent(BeeperStateMachine.beep_delay_over.class, self(),
                    new Duration(100000000l)));
        }
        self().setBeep_count(self().getBeep_count() + 1);
    }

    @Override
    public TransitionRule getTransition(Enum<?> currentState, Event event) {
        States state = (States) currentState;
        Events eventId = (Events) event.getEventId();
        switch (state) {
        case NON_EXISTENT:
            switch (eventId) {
            case START_BEEPING:
                return cannotHappen(currentState, event);
            case BEEP_DELAY_OVER:
                return cannotHappen(currentState, event);
            case STOP_BEEPING:
                return cannotHappen(currentState, event);
            case BEEPING_STOPPED:
                return cannotHappen(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case AWAITING_BEEPER_REQUEST:
            switch (eventId) {
            case START_BEEPING:
                return () -> {
                    Beeping_entry_action();
                    return States.BEEPING;
                };
            case BEEP_DELAY_OVER:
                return ignore(currentState, event);
            case STOP_BEEPING:
                return ignore(currentState, event);
            case BEEPING_STOPPED:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case BEEPING:
            switch (eventId) {
            case START_BEEPING:
                return ignore(currentState, event);
            case BEEP_DELAY_OVER:
                return () -> {
                    Beeping_entry_action();
                    return States.BEEPING;
                };
            case STOP_BEEPING:
                return () -> {
                    Awaiting_Beeper_Request_entry_action();
                    return States.AWAITING_BEEPER_REQUEST;
                };
            case BEEPING_STOPPED:
                return () -> {
                    Awaiting_Beeper_Request_entry_action();
                    return States.AWAITING_BEEPER_REQUEST;
                };
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        default:
            throw new TransitionException(currentState, event, "Unknown state");
        }
    }

    // events
    public static class start_beeping extends Event {
        public start_beeping(Object... data) {
            super(Events.START_BEEPING, data);
        }
    }

    public static class beep_delay_over extends Event {
        public beep_delay_over(Object... data) {
            super(Events.BEEP_DELAY_OVER, data);
        }
    }

    public static class stop_beeping extends Event {
        public stop_beeping(Object... data) {
            super(Events.STOP_BEEPING, data);
        }
    }

    public static class beeping_stopped extends Event {
        public beeping_stopped(Object... data) {
            super(Events.BEEPING_STOPPED, data);
        }
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public Beeper self() {
        return (Beeper) super.self();
    }

}
