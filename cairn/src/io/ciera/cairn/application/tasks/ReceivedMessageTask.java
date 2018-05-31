package io.ciera.cairn.application.tasks;

import io.ciera.cairn.application.ApplicationTask;
import io.ciera.cairn.application.TaskPriority;

public abstract class ReceivedMessageTask extends ApplicationTask {

    @Override
    public int getPriority() {
        return TaskPriority.RECEIVED_MESSAGE_PRIORITY;
    }

}
