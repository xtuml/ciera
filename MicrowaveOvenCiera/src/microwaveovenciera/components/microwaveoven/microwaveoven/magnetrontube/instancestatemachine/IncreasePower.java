package microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class IncreasePower extends Event {
    
    private static final int eventNumber = 1;
    private static final int classNumber = 2;

    public IncreasePower(EventTarget t, boolean ts) {
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
