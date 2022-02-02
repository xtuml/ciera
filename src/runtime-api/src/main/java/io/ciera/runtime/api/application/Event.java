package io.ciera.runtime.api.application;

import io.ciera.runtime.api.types.UniqueId;

public interface Event {

    public int getEventId();

    public String getName();

    public UniqueId getEventHandle();

    public Object getData(int index);

}
