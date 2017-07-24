package microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine;

import ciera.statemachine.Event;

public class Close extends Event {
    
    private static final int eventNumber = 2;
    private static final int classNumber = 5;

    @Override
    public int getEventNumber() {
        return eventNumber;
    }

    @Override
    public int getClassNumber() {
        return classNumber;
    }

}
