package io.ciera.runtime.application;

import io.ciera.runtime.types.UniqueId;

public abstract class Event implements Comparable<Event>, Named {

    private final UniqueId eventHandle;
    private final Enum<?> eventId;
    private final Object[] parameterData;

    public Event(Enum<?> eventId, Object... data) {
        this(UniqueId.random(), eventId, data);
    }

    public Event(UniqueId eventHandle, Enum<?> eventId, Object... data) {
        this.eventHandle = eventHandle;
        this.eventId = eventId;
        this.parameterData = data;
    }

    public Enum<?> getEventId() {
        return eventId;
    }

    @Override
    public String getName() {
        return getClass().getDeclaringClass().getSimpleName() + "." + getClass().getSimpleName() + "["
                + eventHandle.toString().substring(0, 8) + "]";
    }

    public UniqueId getEventHandle() {
        return eventHandle;
    }

    public Object get(int index) {
        if (index >= 0 && index < parameterData.length) {
            return parameterData[index];
        } else {
            throw new IndexOutOfBoundsException(index);
        }
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
