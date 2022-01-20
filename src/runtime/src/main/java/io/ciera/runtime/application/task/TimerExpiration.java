package io.ciera.runtime.application.task;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;

public class TimerExpiration extends Task {

    private Event event;
    private EventTarget target;

    public TimerExpiration(Event event, EventTarget target) {
        this.event = event;
        this.target = target;
    }

    @Override
    public void run() {
        // Handle the event
        target.consumeEvent(event);
    }

}
