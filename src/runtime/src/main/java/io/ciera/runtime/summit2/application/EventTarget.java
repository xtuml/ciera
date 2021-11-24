package io.ciera.runtime.summit2.application;

import io.ciera.runtime.summit2.types.UniqueId;

public interface EventTarget {

    public UniqueId getTargetHandle();
    public void consumeEvent(Event event);

}
