package microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class OvenSafe extends Event {
    
    private static final int eventId = 6;
    private static final int eventNumber = 7;
    private static final int classNumber = 1;
    private static final String eventName = "Oven Safe";

    public OvenSafe() {
    }

    public OvenSafe( EventTarget t, boolean ts ) {
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
