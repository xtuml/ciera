package io.ciera.cairn.application.tasks;

import io.ciera.cairn.application.ApplicationTask;
import io.ciera.cairn.application.TaskPriority;
import io.ciera.summit.interfaces.IMessage;

public abstract class ReceivedMessageTask extends ApplicationTask {
	
	public abstract IMessage getMessage();

	@Override
	public int getPriority() {
		return TaskPriority.RECEIVED_MESSAGE_PRIORITY;
	}

}
