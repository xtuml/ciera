package components.microwaveoven.microwaveoven;

import components.MicrowaveOven;
import components.microwaveoven.datatypes.Tube_Wattage;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.domain.InstanceStateMachine;
import io.ciera.runtime.domain.TransitionRule;
import io.ciera.runtime.exceptions.TransitionException;

public class MagnetronTubeStateMachine extends InstanceStateMachine {

    public static enum States {
        NON_EXISTENT, OUTPUT_POWER_STABLE_AND_OFF, OUTPUT_POWER_STABLE_AND_ON, RAISING_OUTPUT_POWER,
        REDUCING_OUTPUT_POWER;
    }

    private static enum Events {
        POWER_OFF, INCREASE_POWER, DECREASE_POWER, POWER_ON;
    }

    public MagnetronTubeStateMachine(MicrowaveOven domain, Enum<?> initialState, MagnetronTube self) {
        super(domain, initialState, self);
    }

    private void Raising_Output_Power_entry_action() {
        if (self().getCurrent_power_output().equals(Tube_Wattage.LOW)) {
            self().setCurrent_power_output(Tube_Wattage.MED_LOW);
        } else if (self().getCurrent_power_output().equals(Tube_Wattage.MED_LOW)) {
            self().setCurrent_power_output(Tube_Wattage.MEDIUM);
        } else if (self().getCurrent_power_output().equals(Tube_Wattage.MEDIUM)) {
            self().setCurrent_power_output(Tube_Wattage.MED_HIGH);
        } else if (self().getCurrent_power_output().equals(Tube_Wattage.MED_HIGH)) {
            self().setCurrent_power_output(Tube_Wattage.HIGH);
        }
    }

    private void Reducing_Output_Power_entry_action() {
        if (self().getCurrent_power_output().equals(Tube_Wattage.MED_LOW)) {
            self().setCurrent_power_output(Tube_Wattage.LOW);
        } else if (self().getCurrent_power_output().equals(Tube_Wattage.MEDIUM)) {
            self().setCurrent_power_output(Tube_Wattage.MED_LOW);
        } else if (self().getCurrent_power_output().equals(Tube_Wattage.MED_HIGH)) {
            self().setCurrent_power_output(Tube_Wattage.MEDIUM);
        } else if (self().getCurrent_power_output().equals(Tube_Wattage.HIGH)) {
            self().setCurrent_power_output(Tube_Wattage.MED_HIGH);
        }
    }

    @Override
    public TransitionRule getTransition(Enum<?> currentState, Event event) {
        States state = (States) currentState;
        Events eventId = (Events) event.getEventId();
        switch (state) {
        case NON_EXISTENT:
            switch (eventId) {
            case POWER_OFF:
                return cannotHappen(currentState, event);
            case INCREASE_POWER:
                return cannotHappen(currentState, event);
            case DECREASE_POWER:
                return cannotHappen(currentState, event);
            case POWER_ON:
                return cannotHappen(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case OUTPUT_POWER_STABLE_AND_OFF:
            switch (eventId) {
            case POWER_OFF:
                return ignore(currentState, event);
            case INCREASE_POWER:
                return () -> {
                    Raising_Output_Power_entry_action();
                    return States.RAISING_OUTPUT_POWER;
                };
            case DECREASE_POWER:
                return () -> {
                    Reducing_Output_Power_entry_action();
                    return States.REDUCING_OUTPUT_POWER;
                };
            case POWER_ON:
                return () -> States.OUTPUT_POWER_STABLE_AND_ON;
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case OUTPUT_POWER_STABLE_AND_ON:
            switch (eventId) {
            case POWER_OFF:
                return () -> States.OUTPUT_POWER_STABLE_AND_OFF;
            case INCREASE_POWER:
                return () -> {
                    Raising_Output_Power_entry_action();
                    return States.RAISING_OUTPUT_POWER;
                };
            case DECREASE_POWER:
                return () -> {
                    Reducing_Output_Power_entry_action();
                    return States.REDUCING_OUTPUT_POWER;
                };
            case POWER_ON:
                return ignore(currentState, event);
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case RAISING_OUTPUT_POWER:
            switch (eventId) {
            case POWER_OFF:
                return () -> States.OUTPUT_POWER_STABLE_AND_OFF;
            case INCREASE_POWER:
                return () -> {
                    Raising_Output_Power_entry_action();
                    return States.RAISING_OUTPUT_POWER;
                };
            case DECREASE_POWER:
                return () -> {
                    Reducing_Output_Power_entry_action();
                    return States.REDUCING_OUTPUT_POWER;
                };
            case POWER_ON:
                return () -> States.OUTPUT_POWER_STABLE_AND_ON;
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        case REDUCING_OUTPUT_POWER:
            switch (eventId) {
            case POWER_OFF:
                return () -> States.OUTPUT_POWER_STABLE_AND_OFF;
            case INCREASE_POWER:
                return () -> {
                    Raising_Output_Power_entry_action();
                    return States.RAISING_OUTPUT_POWER;
                };
            case DECREASE_POWER:
                return () -> {
                    Reducing_Output_Power_entry_action();
                    return States.REDUCING_OUTPUT_POWER;
                };
            case POWER_ON:
                return () -> States.OUTPUT_POWER_STABLE_AND_ON;
            default:
                throw new TransitionException(currentState, event, "Unknown event in state");
            }
        default:
            throw new TransitionException(currentState, event, "Unknown state");
        }
    }

    // events
    public static class power_off extends Event {
        public power_off(Object... data) {
            super(Events.POWER_OFF, data);
        }
    }

    public static class increase_power extends Event {
        public increase_power(Object... data) {
            super(Events.INCREASE_POWER, data);
        }
    }

    public static class decrease_power extends Event {
        public decrease_power(Object... data) {
            super(Events.DECREASE_POWER, data);
        }
    }

    public static class power_on extends Event {
        public power_on(Object... data) {
            super(Events.POWER_ON, data);
        }
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public MagnetronTube self() {
        return (MagnetronTube) super.self();
    }

}
