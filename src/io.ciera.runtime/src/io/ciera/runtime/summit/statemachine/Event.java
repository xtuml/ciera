package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.instanceloading.AttributeChangedDelta;
import io.ciera.runtime.instanceloading.InstanceCreatedDelta;
import io.ciera.runtime.instanceloading.InstanceDeletedDelta;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.exceptions.StateMachineException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class Event implements IEvent, Comparable<IEvent> {
	
	private EventHandle eventHandle;
	private int populationId;
	private IRunContext runContext;
    private IEventTarget target;
    private boolean toSelf;

    private Object[] dataItems;

    public Event(IRunContext runContext, int populationId) {
    	this(runContext, populationId, new Object[0]);
    }

    public Event(IRunContext runContext, int populationId, Object... data) {
    	this.populationId = populationId;
    	this.runContext = runContext;
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
    public EventHandle getEventHandle() {
    	return eventHandle;
    }
    
    @Override
    public int getPopulationId() {
    	return populationId;
    }

    @Override
    public IEventTarget getTarget() {
        return target;
    }

    @Override
    public EventHandle to(IEventTarget target) {
    	return to(EventHandle.random(), target, false);
    }

    @Override
    public EventHandle toSelf(IEventTarget target) {
    	return to(EventHandle.random(), target, true);
    }

    @Override
    public void to(EventHandle e, IEventTarget target) {
        to(e, target, false);
    }
    
    private EventHandle to(EventHandle e, IEventTarget target, boolean toSelf) {
        this.target = target;
        this.toSelf = toSelf;
        eventHandle = e;
        runContext.registerEvent(this);
        return eventHandle;
    }

    @Override
    public boolean toSelf() {
        return toSelf;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public int compareTo(IEvent e) {
    	return eventHandle.compareTo(e.getEventHandle());
    }
    
    @Override
    public boolean equals(Object o) {
    	if (null != o && o instanceof IEvent) return eventHandle.equals(((IEvent)o).getEventHandle());
    	else return false;
    }
    
    @Override
    public int hashCode() {
    	return eventHandle.hashCode();
    }

    public static class EventCreatedDelta extends InstanceCreatedDelta {
    	public EventCreatedDelta(IEvent e) {
    		super(e, e.getName());
    	}
    }

    public static class EventDeletedDelta extends InstanceDeletedDelta {
    	public EventDeletedDelta(IEvent e) {
    		super(e, e.getName());
    	}
    }

    public static class EventAttributeChangedDelta extends AttributeChangedDelta {
    	public EventAttributeChangedDelta(IEvent e, String attributeName, Object oldValue, Object newValue) {
    		super(e, e.getName(), attributeName, oldValue, newValue);
    	}
    }

}
