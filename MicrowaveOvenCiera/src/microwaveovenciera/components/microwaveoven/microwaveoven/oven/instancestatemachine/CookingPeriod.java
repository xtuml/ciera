package microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.exceptions.SameDataException;

public class CookingPeriod extends Event {
    
    private static final int eventNumber = 8;
    private static final int classNumber = 1;
    
    private int period;

    public CookingPeriod( int period ) {
        this.period = period;
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
    public Object getData(String id) throws SameDataException {
        if ( id.equals( "period" ) ) return period;
        else throw new SameDataException( "Event does not contain required data." );
    }

}
