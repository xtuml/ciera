package io.ciera.runtime.application;

import io.ciera.runtime.domain.Message;
import io.ciera.runtime.types.UniqueId;

public interface MessageTarget {

    public UniqueId getTargetHandle();

    public void deliver(Message message);

    public void attachTo(ExecutionContext context);

}
