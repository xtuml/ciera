package microwaveovenciera.components.microwaveoven.microwaveoven.internallight.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class SwitchOff extends Event {
    
    private static final int eventNumber = 2;
    private static final int classNumber = 3;

    public SwitchOff(EventTarget t, boolean ts) {
        super(t, ts);
    }

    @Override
    public int getEventNumber() {
        return eventNumber;
    }

    @Override
    public int getClassNumber() {
        return classNumber;
    }

}
