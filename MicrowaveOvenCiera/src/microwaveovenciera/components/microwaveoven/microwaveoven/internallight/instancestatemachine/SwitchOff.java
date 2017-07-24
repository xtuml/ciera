package microwaveovenciera.components.microwaveoven.microwaveoven.internallight.instancestatemachine;

import ciera.statemachine.Event;

public class SwitchOff extends Event {
    
    private static final int eventNumber = 2;
    private static final int classNumber = 3;

    @Override
    public int getEventNumber() {
        return eventNumber;
    }

    @Override
    public int getClassNumber() {
        return classNumber;
    }

}
