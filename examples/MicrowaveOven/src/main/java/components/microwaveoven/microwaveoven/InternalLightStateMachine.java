package components.microwaveoven.microwaveoven;

import components.MicrowaveOven;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.domain.InstanceStateMachine;
import io.ciera.runtime.domain.TransitionRule;
import io.ciera.runtime.exceptions.TransitionException;

public class InternalLightStateMachine extends InstanceStateMachine {

    public static enum States {
        NON_EXISTENT, OFF, ON;
    }

    private static enum Events {
        SWITCH_OFF, SWITCH_ON;
    }

    public InternalLightStateMachine(MicrowaveOven domain, Enum<?> initialState, InternalLight self) {
        super(domain, initialState, self);
    }

    @Override
    public TransitionRule getTransition(Enum<?> currentState, Event event) {
        States state = (States) currentState;
        Events eventId = (Events) event.getEventId();
        switch (state) {
        case NON_EXISTENT:
            switch (eventId) {
            case SWITCH_OFF:
                return cannotHappen(currentState, event);
            case SWITCH_ON:
                return cannotHappen(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case OFF:
            switch (eventId) {
            case SWITCH_OFF:
                return ignore(currentState, event);
            case SWITCH_ON:
                return () -> States.ON;
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case ON:
            switch (eventId) {
            case SWITCH_OFF:
                return () -> States.OFF;
            case SWITCH_ON:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        default:
            throw new TransitionException(currentState, event, "Unknown state");
        }
    }

    // events
    public static class switch_off extends Event {
        public switch_off(Object... data) {
            super(Events.SWITCH_OFF, data);
        }
    }

    public static class switch_on extends Event {
        public switch_on(Object... data) {
            super(Events.SWITCH_ON, data);
        }
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public InternalLight self() {
        return (InternalLight) super.self();
    }

}
