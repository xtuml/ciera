package io.ciera.runtime.summit.application.tasks;

import io.ciera.runtime.summit.application.ApplicationTask;
import io.ciera.runtime.summit.application.TaskPriority;

//import io.ciera.runtime.summit.time.Timer;

public abstract class PoppedTimerTask extends ApplicationTask {

    //public abstract Timer getTimer();

    @Override
    public int getPriority() {
        return TaskPriority.POPPED_TIMER_PRIORITY;
    }

}
