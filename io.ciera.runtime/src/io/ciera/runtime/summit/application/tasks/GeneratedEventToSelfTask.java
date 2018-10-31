package io.ciera.runtime.summit.application.tasks;

import io.ciera.runtime.summit.application.TaskPriority;

public abstract class GeneratedEventToSelfTask extends GeneratedEventTask {

    @Override
    public int getPriority() {
        return TaskPriority.GENERATED_EVENT_TO_SELF_PRIORITY;
    }

}
