package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;

public class TimerExpiration extends GeneratedEvent {

    public TimerExpiration(Event event, EventTarget target) {
        super(event, target, ExecutionContext.ExecutionMode.INTERLEAVED);
    }

}
