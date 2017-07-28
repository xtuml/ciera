package microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class Close extends Event {
    
    private static final int eventId = 1;
    private static final int eventNumber = 2;
    private static final int classNumber = 5;
    private static final String eventName = "Close";

    public Close() {
    }

    public Close( EventTarget t, boolean ts ) {
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
