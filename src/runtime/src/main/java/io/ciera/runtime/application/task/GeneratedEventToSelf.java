package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;

public class GeneratedEventToSelf extends GeneratedEvent {

    private static final long serialVersionUID = 1L;

    public GeneratedEventToSelf(Event event, EventTarget target) {
        super(event, target, null);
    }

    @Override
    public int getPriority() {
        return Task.SELF_EVENT_PRIORITY;
    }

}
