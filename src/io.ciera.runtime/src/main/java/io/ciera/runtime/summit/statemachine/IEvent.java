package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IEvent {

	public EventHandle getEventHandle();
	
	public int getPopulationId();
	
    public String getName();
    
    public String getClassName();

    public int getId();

    public Object get(int index) throws XtumlException;

    public IEventTarget getTarget();

    public boolean toSelf();

    public EventHandle to(IEventTarget target);

    public void to(EventHandle e, IEventTarget target);

    public EventHandle toSelf(IEventTarget target);
    
}
