package ciera.statemachine;

import ciera.exceptions.XtumlException;

public abstract class Event {
    
    private EventTarget target;
    private boolean toSelf;
    
    public Event() {
    }
    
    public Event( EventTarget t, boolean ts ) {
        target = t;
        toSelf = ts;
    }
    
    public EventTarget getTarget() {
        return target;
    }
    
    public void setTarget( EventTarget t ) {
        target = t;
    }

    public boolean toSelf() {
        return toSelf;
    }
    
    public void setToSelf( boolean ts ) {
        toSelf = ts;
    }
    
    public Object getData( String id ) throws XtumlException {
        return null;
    }
    
    public void generate() throws XtumlException {
        if ( toSelf() ) target.generateToSelf( this );
        else target.generateTo( this );
    }

    public abstract int getEventId();
    public abstract int getEventNumber();
    public abstract int getClassNumber();

}
