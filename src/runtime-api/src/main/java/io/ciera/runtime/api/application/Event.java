package io.ciera.runtime.api.application;

import io.ciera.runtime.api.types.UniqueId;

public interface Event {

    public Enum<?> getEventId();

    public UniqueId getEventHandle();

    public Object get(int index);

}
