package microwaveovenciera.components.microwaveoven.microwaveoven.turntable.instancestatemachine;

import ciera.statemachine.Event;

public class Spin extends Event {
    
    private static final int eventNumber = 1;
    private static final int classNumber = 6;

    @Override
    public int getEventNumber() {
        return eventNumber;
    }

    @Override
    public int getClassNumber() {
        return classNumber;
    }

}
