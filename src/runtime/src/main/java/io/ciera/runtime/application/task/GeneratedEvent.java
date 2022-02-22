package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.ExecutionContext.ExecutionMode;

public class GeneratedEvent extends Task {

    private Event event;
    private EventTarget target;
    private ExecutionContext.ExecutionMode executionMode;

    public GeneratedEvent(Event event, EventTarget target, ExecutionContext.ExecutionMode executionMode) {
        this.event = event;
        this.target = target;
        this.executionMode = executionMode;
    }

    @Override
    public void run() {
        target.consumeEvent(event);
    }

    @Override
    public int getPriority() {
        if (executionMode == ExecutionMode.SEQUENTIAL) {
            return Task.SEQUENTIAL_EVENT_PRIORITY;
        } else {
            return super.getPriority();
        }
    }

}
