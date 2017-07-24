package microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class OvenInitialised extends Event {
    
    private static final int eventNumber = 2;
    private static final int classNumber = 1;

    public OvenInitialised() {
    }

    public OvenInitialised( EventTarget t, boolean ts ) {
        super( t, ts );
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
