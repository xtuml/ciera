package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IEvent {

    public String getName();

    public int getId();

    public Object get(int index) throws XtumlException;

    public IEventTarget getTarget();

    public boolean toSelf();

    public EventHandle to(IEventTarget target);

    public EventHandle toSelf(IEventTarget target);
    
}
