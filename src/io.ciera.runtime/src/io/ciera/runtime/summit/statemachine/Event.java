package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.exceptions.StateMachineException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

public abstract class Event implements IEvent {
	
	private UniqueId eventId;
	private IRunContext context;

    private IEventTarget target;
    private boolean toSelf;

    private Object[] dataItems;

    public Event(IRunContext runContext) {
    	this(runContext, new Object[0]);
    }

    public Event(IRunContext runContext, Object... data) {
    	eventId = UniqueId.random();
    	context = runContext;
        dataItems = data;
        target = null;
        toSelf = false;
        context.registerEvent(this);
    }

    @Override
    public Object get(int index) throws XtumlException {
        if (index >= 0 && index < dataItems.length) {
            return dataItems[index];
        } else
            throw new StateMachineException("Invalid index");
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

	@Override
	public void deregister() {
		context.deregisterEvent(this);
	}

	@Override
	public String serialize() {
		return "\"" + eventId.toString() + "\"";
	}

}
