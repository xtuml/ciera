package ciera.statemachine;

import ciera.statemachine.exceptions.SameDataException;

public abstract class Event {
    
    private EventTarget target;
    private boolean toSelf;
    
    public Event( EventTarget t, boolean ts ) {
        target = t;
        toSelf = ts;
    }
    
    public EventTarget getTarget() {
        return target;
    }

    public boolean toSelf() {
        return toSelf;
    }
    
    public Object getData( String id ) throws SameDataException {
        return null;
    }

    public abstract int getEventNumber();
    public abstract int getClassNumber();

}
