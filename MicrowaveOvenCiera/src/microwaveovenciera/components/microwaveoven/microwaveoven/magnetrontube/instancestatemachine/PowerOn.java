package microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class PowerOn extends Event {
    
    private static final int eventId = 2;
    private static final int eventNumber = 3;
    private static final int classNumber = 4;

    public PowerOn() {
    }

    public PowerOn( EventTarget t, boolean ts ) {
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
