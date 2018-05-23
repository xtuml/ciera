package io.ciera.cairn.application.tasks;

import io.ciera.cairn.application.ApplicationTask;
import io.ciera.cairn.application.TaskPriority;
import io.ciera.summit.exceptions.XtumlException;

public final class HaltExecutionTask extends ApplicationTask {

    @Override
    public int getPriority() {
        return TaskPriority.HALT_EXECUTION_PRIORITY;
    }

    @Override
    public void run() throws XtumlException {}

}
