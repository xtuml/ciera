package io.ciera.runtime.api.application;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.types.UniqueId;

public interface EventTarget {

    public UniqueId getTargetId();

    public void consumeEvent(Event event);

    public ExecutionContext getContext();
    
    public Domain getDomain();

}
