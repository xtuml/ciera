package io.ciera.runtime.summit.application.tasks;

import io.ciera.runtime.summit.application.ApplicationTask;
import io.ciera.runtime.summit.application.TaskPriority;
import io.ciera.summit.statemachine.IEvent;

public abstract class GeneratedEventTask extends ApplicationTask {

    public abstract IEvent getEvent();

    @Override
    public int getPriority() {
        return TaskPriority.GENERATED_EVENT_PRIORITY;
    }

}
