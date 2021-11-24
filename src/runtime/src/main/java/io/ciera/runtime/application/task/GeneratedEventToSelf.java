package io.ciera.runtime.application.task;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Task;

public class GeneratedEventToSelf extends GeneratedEvent {

    public GeneratedEventToSelf(ExecutionContext context, Event event, EventTarget target) {
        super(context, event, target);
    }

    @Override
    public int getPriority() {
        return Task.SELF_EVENT_PRIORITY;
    }

}
