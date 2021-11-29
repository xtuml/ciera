package io.ciera.runtime.application.task;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Task;
import io.ciera.runtime.application.Timer;

public class TimerExpiration extends Task {

    private Timer timer;
    private Event event;
    private EventTarget target;

    public TimerExpiration(ExecutionContext context, Timer timer, Event event, EventTarget target) {
        super(context);
        this.timer = timer;
        this.event = event;
        this.target = target;
    }

    @Override
    public void run() {
        // Handle the event
        target.consumeEvent(event);

        // Re-schedule the timer if recurring
        if (timer.isRecurring()) {
            timer.reset();
        }
    }

}
