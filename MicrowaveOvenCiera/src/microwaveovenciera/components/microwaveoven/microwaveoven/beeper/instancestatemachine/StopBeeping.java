package microwaveovenciera.components.microwaveoven.microwaveoven.beeper.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class StopBeeping extends Event {
    
    private static final int eventId = 3;
    private static final int eventNumber = 4;
    private static final int classNumber = 4;
    private static final String eventName = "Stop Beeping";

    public StopBeeping() {
    }

    public StopBeeping( EventTarget t, boolean ts ) {
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
