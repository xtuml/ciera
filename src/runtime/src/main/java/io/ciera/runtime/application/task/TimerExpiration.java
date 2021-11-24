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
        // Queue the event
        getContext().addTask(new GeneratedEvent(getContext(), event, target));
        if (timer.isRecurring()) {
            // Re-schedule the timer
            timer.reset();
            getContext().scheduleEvent(event, target, timer);
        } else {
            getContext().getInstancePopulation().removeTimer(timer.getTimerHandle());
        }
    }

}
