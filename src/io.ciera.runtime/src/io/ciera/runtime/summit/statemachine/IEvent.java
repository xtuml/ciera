package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IEvent {
	
	public Object get(int index) throws XtumlException;

	public IEventTarget getTarget();
	public boolean toSelf();
	public IEvent to(IEventTarget target);
	public IEvent toSelf(IEventTarget target);

}
