package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext.ExecutionMode;

public class GeneratedEvent extends Task {

    private Event event;
    private EventTarget target;

    public GeneratedEvent(Event event, EventTarget target) {
        this.event = event;
        this.target = target;
    }

    @Override
    public void run() {
        target.consumeEvent(event);
    }

    @Override
    public int getPriority() {
        if (getContext().getExecutionMode() == ExecutionMode.SEQUENTIAL) {
            return Task.SEQUENTIAL_EVENT_PRIORITY;
        } else {
            return super.getPriority();
        }
    }

}
