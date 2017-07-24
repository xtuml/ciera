package microwaveovenciera.components.microwaveoven.microwaveoven.internallight.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class SwitchOn extends Event {
    
    private static final int eventNumber = 1;
    private static final int classNumber = 3;

    public SwitchOn() {
    }

    public SwitchOn( EventTarget t, boolean ts ) {
        super( t, ts );
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
