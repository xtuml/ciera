package io.ciera.runtime.summit.application.tasks;

import io.ciera.runtime.summit.application.ApplicationTask;
import io.ciera.runtime.summit.application.TaskPriority;
import io.ciera.runtime.summit.exceptions.XtumlException;

public final class HaltExecutionTask extends ApplicationTask {

    @Override
    public int getPriority() {
        return TaskPriority.HALT_EXECUTION_PRIORITY;
    }

    @Override
    public void run() throws XtumlException {
    }

}
