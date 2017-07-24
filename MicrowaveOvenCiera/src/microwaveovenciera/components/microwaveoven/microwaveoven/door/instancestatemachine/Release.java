package microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class Release extends Event {
    
    private static final int eventNumber = 1;
    private static final int classNumber = 5;

    public Release(EventTarget t, boolean ts) {
        super(t, ts);
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
