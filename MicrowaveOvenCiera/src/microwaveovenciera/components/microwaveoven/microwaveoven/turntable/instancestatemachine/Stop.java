package microwaveovenciera.components.microwaveoven.microwaveoven.turntable.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class Stop extends Event {
    
    private static final int eventId = 1;
    private static final int eventNumber = 2;
    private static final int classNumber = 6;

    public Stop() {
    }

    public Stop( EventTarget t, boolean ts ) {
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
