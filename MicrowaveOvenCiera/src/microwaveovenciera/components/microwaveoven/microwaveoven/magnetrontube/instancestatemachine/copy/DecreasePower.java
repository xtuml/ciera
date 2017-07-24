package microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine.copy;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class DecreasePower extends Event {
    
    private static final int eventNumber = 2;
    private static final int classNumber = 2;

    public DecreasePower(EventTarget t, boolean ts) {
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
