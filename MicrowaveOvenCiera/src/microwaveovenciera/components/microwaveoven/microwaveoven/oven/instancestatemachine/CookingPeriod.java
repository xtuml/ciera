package microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine;

import ciera.exceptions.SameDataException;
import ciera.exceptions.XtumlException;
import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class CookingPeriod extends Event {
    
    private static final int eventId = 7;
    private static final int eventNumber = 8;
    private static final int classNumber = 1;
    
    private int period;

    public CookingPeriod( int period ) {
        this.period = period;
    }

    public CookingPeriod( EventTarget t, boolean ts, int period ) {
        super( t, ts );
        this.period = period;
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
    public Object getData(String id) throws XtumlException {
        if ( id.equals( "period" ) ) return period;
        else throw new SameDataException( "Event does not contain required data." );
    }

}
