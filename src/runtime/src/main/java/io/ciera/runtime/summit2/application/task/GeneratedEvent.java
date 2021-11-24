package io.ciera.runtime.summit2.application.task;

import io.ciera.runtime.summit2.application.Event;
import io.ciera.runtime.summit2.application.EventTarget;
import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.ExecutionMode;
import io.ciera.runtime.summit2.application.Task;

public class GeneratedEvent extends Task {

    private Event event;
    private EventTarget target;

    public GeneratedEvent(ExecutionContext context, Event event, EventTarget target) {
        super(context);
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
