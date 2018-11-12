package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.exceptions.StateMachineException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class Event implements IEvent {
	
	private IRunContext context;

    private IEventTarget target;
    private boolean toSelf;

    private Object[] dataItems;

    public Event(IRunContext runContext) {
    	this(runContext, new Object[0]);
    }

    public Event(IRunContext runContext, Object... data) {
    	context = runContext;
        dataItems = data;
        target = null;
        toSelf = false;
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
    public EventHandle to(IEventTarget target) {
        this.target = target;
        toSelf = false;
        return context.registerEvent(this);
    }

    @Override
    public EventHandle toSelf(IEventTarget target) {
        this.target = target;
        toSelf = true;
        return context.registerEvent(this);
    }

    @Override
    public boolean toSelf() {
        return toSelf;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

}
