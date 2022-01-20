package io.ciera.runtime.api.application;

public interface EventTarget {

    public void consumeEvent(Event event);

    public void attachTo(ExecutionContext context);

    public ExecutionContext getContext();

}
