package ciera.statemachine;

import ciera.application.XtumlApplication;
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
    
    public boolean toSelf() {
        return toSelf;
    }
    
    public Object getData( String id ) throws XtumlException {
        return null;
    }
    
    public void generate() throws XtumlException {
        XtumlApplication.app.getExecutor().getCurrentTask().generateTo( this );
    }

    public void generateTo( EventTarget target ) throws XtumlException {
        this.target = target;
        generate();
    }

    public void generateToSelf( EventTarget target ) throws XtumlException {
        toSelf = true;
        generateTo( target );
    }

    public abstract int getEventId();
    public abstract int getEventNumber();
    public abstract int getClassNumber();
    public abstract String getEventName();

}
