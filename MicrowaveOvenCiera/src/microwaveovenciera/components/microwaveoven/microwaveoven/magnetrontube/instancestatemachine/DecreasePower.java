package microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class DecreasePower extends Event {
    
    private static final int eventId = 1;
    private static final int eventNumber = 2;
    private static final int classNumber = 2;
    private static final String eventName = "Decrease Power";

    public DecreasePower() {
    }

    public DecreasePower( EventTarget t, boolean ts ) {
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

    @Override
    public String getEventName() {
        return eventName;
    }

}
