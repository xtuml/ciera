package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.StateMachineException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class Event implements IEvent {
    
    private IEventTarget target;
    private boolean toSelf;
	
	private Object[] dataItems;
	
	public Event() {
		dataItems = new Object[]{};
		target = null;
		toSelf = false;
	}
	
	public Event(Object ... dataItems) {
		this.dataItems = dataItems;
		target = null;
		toSelf = false;
	}

	@Override
	public Object get(int index) throws XtumlException {
		if (index >= 0 && index < dataItems.length) {
			return dataItems[index];
		}
		else throw new StateMachineException("Invalid index");
	}

    @Override
    public IEventTarget getTarget() {
        return target;
    }

    @Override
    public IEvent to(IEventTarget target) {
        this.target = target;
        toSelf = false;
        return this;
    }

    @Override
    public IEvent toSelf(IEventTarget target) {
        this.target = target;
        toSelf = true;
        return this;
    }

    @Override
    public boolean toSelf() {
        return toSelf;
    }

    @Override
    public IEvent value() {
        return this;
    }
    
    @Override
    public String getName() {
    	return getClass().getSimpleName();
    }

}
