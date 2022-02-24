package io.ciera.runtime.api.application;

import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.Message;
import io.ciera.runtime.api.types.UniqueId;

public interface MessageTarget {

    public UniqueId getTargetId();

    public void deliver(Message message);

    public ExecutionContext getContext();

    public Domain getDomain();

}
