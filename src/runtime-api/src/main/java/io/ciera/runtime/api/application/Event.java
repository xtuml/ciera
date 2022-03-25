package io.ciera.runtime.api.application;

import java.io.Serializable;

import io.ciera.runtime.api.types.UniqueId;

public interface Event extends Serializable {

    public int getEventId();

    public String getName();

    public UniqueId getEventHandle();

    public Object getData(int index);

}
