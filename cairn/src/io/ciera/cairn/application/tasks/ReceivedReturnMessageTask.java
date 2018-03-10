package io.ciera.cairn.application.tasks;

import io.ciera.cairn.application.TaskPriority;

public abstract class ReceivedReturnMessageTask extends ReceivedMessageTask {
	
	@Override
	public int getPriority() {
		return TaskPriority.RECEIVED_RETURN_MESSAGE_PRIORITY;
	}

}
