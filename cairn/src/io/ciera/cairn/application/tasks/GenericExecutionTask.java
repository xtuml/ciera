package io.ciera.cairn.application.tasks;

import io.ciera.cairn.application.ApplicationTask;
import io.ciera.cairn.application.TaskPriority;

public abstract class GenericExecutionTask extends ApplicationTask {

	@Override
	public int getPriority() {
		return TaskPriority.GENERIC_EXECUTION_PRIORITY;
	}

}
