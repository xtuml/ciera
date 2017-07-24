package microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine;

import ciera.statemachine.Event;

public class PowerOn extends Event {
    
    private static final int eventNumber = 3;
    private static final int classNumber = 4;

    @Override
    public int getEventNumber() {
        return eventNumber;
    }

    @Override
    public int getClassNumber() {
        return classNumber;
    }

}
