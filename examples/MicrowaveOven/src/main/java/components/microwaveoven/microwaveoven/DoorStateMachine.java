package components.microwaveoven.microwaveoven;

import components.MicrowaveOven;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.domain.InstanceStateMachine;
import io.ciera.runtime.domain.TransitionRule;
import io.ciera.runtime.exceptions.TransitionException;

public class DoorStateMachine extends InstanceStateMachine {

    public static enum States {
        NON_EXISTENT, OPEN, CLOSED;
    }

    private static enum Events {
        RELEASE, CLOSE;
    }

    public DoorStateMachine(MicrowaveOven domain, Enum<?> initialState, Door self) {
        super(domain, initialState, self);
    }

    private void Closed_entry_action() {
        self().setIs_secure(true);
    }

    private void Open_entry_action() {
        self().setIs_secure(false);
        Oven oven = self().R4_provides_access_to_Oven();
        oven.getContext().generateEvent(OvenStateMachine.cancel_cooking.class, oven);
    }

    @Override
    public TransitionRule getTransition(Enum<?> currentState, Event event) {
        States state = (States) currentState;
        Events eventId = (Events) event.getEventId();
        switch (state) {
        case NON_EXISTENT:
            switch (eventId) {
            case RELEASE:
                return cannotHappen(currentState, event);
            case CLOSE:
                return cannotHappen(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case OPEN:
            switch (eventId) {
            case RELEASE:
                return ignore(currentState, event);
            case CLOSE:
                return () -> {
                    Closed_entry_action();
                    return States.CLOSED;
                };
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case CLOSED:
            switch (eventId) {
            case RELEASE:
                return () -> {
                    Open_entry_action();
                    return States.OPEN;
                };
            case CLOSE:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        default:
            throw new TransitionException(currentState, event, "Unknown state");
        }
    }

    // events
    public static class release extends Event {
        public release(Object... data) {
            super(Events.RELEASE, data);
        }
    }

    public static class close extends Event {
        public close(Object... data) {
            super(Events.CLOSE, data);
        }
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public Door self() {
        return (Door) super.self();
    }

}
