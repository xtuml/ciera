package io.ciera.runtime.application;

import io.ciera.runtime.domain.Message;

public interface MessageTarget {

    public void deliver(Message message);

    public void attachTo(ExecutionContext context);

    public ExecutionContext getContext();

}
