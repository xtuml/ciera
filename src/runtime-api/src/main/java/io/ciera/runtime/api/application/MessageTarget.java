package io.ciera.runtime.api.application;

import io.ciera.runtime.api.domain.Message;

public interface MessageTarget {

    public void deliver(Message message);

    public void attachTo(ExecutionContext context);

    public ExecutionContext getContext();

}
