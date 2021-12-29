package io.ciera.runtime.application.task;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Task;

public class TimerExpiration extends Task {

    private Event event;
    private EventTarget target;

    public TimerExpiration(ExecutionContext context, Event event, EventTarget target) {
        super(context);
        this.event = event;
        this.target = target;
    }

    @Override
    public void run() {
        // Handle the event
        target.consumeEvent(event);
    }

}
