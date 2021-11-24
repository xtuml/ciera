package io.ciera.runtime.summit2.application.task;

import io.ciera.runtime.summit2.application.Event;
import io.ciera.runtime.summit2.application.EventTarget;
import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Task;

public class GeneratedEventToSelf extends GeneratedEvent {

    public GeneratedEventToSelf(ExecutionContext context, Event event, EventTarget target) {
        super(context, event, target);
    }
    
    @Override
    public int getPriority() {
        return Task.SELF_EVENT_PRIORITY;
    }

}
