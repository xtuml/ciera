package io.ciera.runtime.application;

import io.ciera.runtime.types.UniqueId;

public interface EventTarget {

    public UniqueId getTargetHandle();

    public void consumeEvent(Event event);

}
