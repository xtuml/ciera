package io.ciera.cairn.application.tasks;

import io.ciera.cairn.application.ApplicationTask;
import io.ciera.cairn.application.TaskPriority;
import io.ciera.summit.statemachines.IEvent;

public abstract class GeneratedEventTask extends ApplicationTask {

    public abstract IEvent getEvent();

    @Override
    public int getPriority() {
        return TaskPriority.GENERATED_EVENT_PRIORITY;
    }

}
