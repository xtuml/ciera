package io.ciera.runtime.summit.application.tasks;

import io.ciera.runtime.summit.application.TaskPriority;

public abstract class ReceivedReturnMessageTask extends ReceivedMessageTask {

    @Override
    public int getPriority() {
        return TaskPriority.RECEIVED_RETURN_MESSAGE_PRIORITY;
    }

}
