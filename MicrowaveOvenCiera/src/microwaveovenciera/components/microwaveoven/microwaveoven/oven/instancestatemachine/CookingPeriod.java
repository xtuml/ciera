package microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class CookingPeriod extends Event {
    
    private static final int eventNumber = 8;
    private static final int classNumber = 1;
    
    private int period;

    public CookingPeriod(EventTarget t, boolean ts, int period ) {
        super(t, ts);
        this.period = period;
    }
    
    public int getPeriod() {
        return period;
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
