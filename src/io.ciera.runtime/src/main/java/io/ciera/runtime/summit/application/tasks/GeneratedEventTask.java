package io.ciera.runtime.summit.application.tasks;

import io.ciera.runtime.summit.application.ApplicationTask;
import io.ciera.runtime.summit.application.TaskPriority;

public abstract class GeneratedEventTask extends ApplicationTask {

    @Override
    public int getPriority() {
        return TaskPriority.GENERATED_EVENT_PRIORITY;
    }

}
