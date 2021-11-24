package io.ciera.runtime.summit2.application;

import io.ciera.runtime.summit2.types.EventHandle;

public class Event {

    private EventHandle eventHandle;

    public String getName() {

        return getClass().getSimpleName() + "[" + eventHandle.toString() + "]";
    }

    public int getId() {
        // TODO
        return 0;
    }

}
