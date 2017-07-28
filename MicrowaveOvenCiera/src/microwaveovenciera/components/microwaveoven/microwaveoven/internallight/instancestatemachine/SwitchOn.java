package microwaveovenciera.components.microwaveoven.microwaveoven.internallight.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class SwitchOn extends Event {
    
    private static final int eventId = 0;
    private static final int eventNumber = 1;
    private static final int classNumber = 3;
    private static final String eventName = "Switch On";

    public SwitchOn() {
    }

    public SwitchOn( EventTarget t, boolean ts ) {
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
