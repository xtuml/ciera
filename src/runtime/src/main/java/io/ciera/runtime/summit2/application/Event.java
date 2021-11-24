package io.ciera.runtime.summit2.application;

import io.ciera.runtime.summit2.types.EventHandle;
import io.ciera.runtime.summit2.types.UniqueId;

public abstract class Event implements Comparable<Event> {

    private EventHandle eventHandle;
    private int eventId;
    private UniqueId targetHandle;
    private Object[] parameterData;

    public Event(int eventId, UniqueId targetHandle, Object... data) {
        this(new EventHandle(), eventId, targetHandle, data);
    }

    public Event(EventHandle eventHandle, int eventId, UniqueId targetHandle, Object... data) {
        this.eventHandle = eventHandle;
        this.eventId = eventId;
        this.parameterData = data;
    }

    public int getEventId() {
        return eventId;
    }

    public String getName() {
        return getClass().getSimpleName() + "[" + eventHandle.toString() + "]";
    }

    public EventHandle getEventHandle() {
        return eventHandle;
    }

    public Object get(int index) {
        if (index >= 0 && index < parameterData.length) {
            return parameterData[index];
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    public UniqueId getTargetHandle() {
        return targetHandle;
    }

    @Override
    public int compareTo(Event e) {
        return eventHandle.compareTo(e.getEventHandle());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Event && eventHandle.equals(((Event) o).getEventHandle());
    }

    @Override
    public int hashCode() {
        return eventHandle.hashCode();
    }

}
