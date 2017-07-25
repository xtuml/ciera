package microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class CookingPeriodOver extends Event {
    
    private static final int eventId = 4;
    private static final int eventNumber = 5;
    private static final int classNumber = 1;

    public CookingPeriodOver() {
    }

    public CookingPeriodOver( EventTarget t, boolean ts ) {
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
