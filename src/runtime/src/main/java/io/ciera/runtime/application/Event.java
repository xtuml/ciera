package io.ciera.runtime.application;

import io.ciera.runtime.types.EventHandle;
import io.ciera.runtime.types.UniqueId;

public abstract class Event implements Comparable<Event>, Named {

    private final EventHandle eventHandle;
    private final int eventId;
    private final UniqueId targetHandle;
    private final Object[] parameterData;

    public Event(int eventId, UniqueId targetHandle, Object... data) {
        this(new EventHandle(UniqueId.random()), eventId, targetHandle, data);
    }

    public Event(EventHandle eventHandle, int eventId, UniqueId targetHandle, Object... data) {
        this.eventHandle = eventHandle;
        this.eventId = eventId;
        this.targetHandle = targetHandle;
        this.parameterData = data;
    }

    public int getEventId() {
        return eventId;
    }

    @Override
    public String getName() {
        return getClass().getDeclaringClass().getSimpleName() + "." + getClass().getSimpleName() + "["
                + eventHandle.toString().substring(0, 8) + "]";
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

    @Override
    public String toString() {
        return getName();
    }

}
