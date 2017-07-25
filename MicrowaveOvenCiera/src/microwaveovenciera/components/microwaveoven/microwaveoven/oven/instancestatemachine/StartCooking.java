package microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class StartCooking extends Event {
    
    private static final int eventId = 2;
    private static final int eventNumber = 3;
    private static final int classNumber = 1;

    public StartCooking() {
    }

    public StartCooking( EventTarget t, boolean ts ) {
        super( t, ts );
    }

    @Override
    public int getEventId() {
        return eventId;
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
