package io.ciera.runtime.summit.application.tasks;

import io.ciera.runtime.summit.application.ApplicationTask;
import io.ciera.runtime.summit.application.TaskPriority;

public abstract class TimerExpiredTask extends ApplicationTask {

    @Override
    public int getPriority() {
        return TaskPriority.TIMER_EXPIRED_PRIORITY;
    }

}
