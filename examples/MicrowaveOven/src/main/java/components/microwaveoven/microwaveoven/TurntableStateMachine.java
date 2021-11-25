package components.microwaveoven.microwaveoven;

import components.MicrowaveOven;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.domain.InstanceStateMachine;
import io.ciera.runtime.domain.TransitionRule;
import io.ciera.runtime.exceptions.TransitionException;

public class TurntableStateMachine extends InstanceStateMachine {

    public static enum States {
        NON_EXISTENT, STATIONARY, ROTATING;
    }

    private static enum Events {
        SPIN, STOP;
    }

    public TurntableStateMachine(MicrowaveOven domain, Enum<?> initialState, Turntable self) {
        super(domain, initialState, self);
    }

    @Override
    public TransitionRule getTransition(Enum<?> currentState, Event event) {
        States state = (States) currentState;
        Events eventId = (Events) event.getEventId();
        switch (state) {
        case NON_EXISTENT:
            switch (eventId) {
            case SPIN:
                return cannotHappen(currentState, event);
            case STOP:
                return cannotHappen(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case STATIONARY:
            switch (eventId) {
            case SPIN:
                return () -> States.ROTATING;
            case STOP:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case ROTATING:
            switch (eventId) {
            case SPIN:
                return ignore(currentState, event);
            case STOP:
                return () -> States.STATIONARY;
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        default:
            throw new TransitionException(currentState, event, "Unknown state");
        }
    }

    // events
    public static class spin extends Event {
        public spin(Object... data) {
            super(Events.SPIN, data);
        }
    }

    public static class stop extends Event {
        public stop(Object... data) {
            super(Events.STOP, data);
        }
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public Turntable self() {
        return (Turntable) super.self();
    }

}
