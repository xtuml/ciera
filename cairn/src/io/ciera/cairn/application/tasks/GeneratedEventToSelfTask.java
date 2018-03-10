package io.ciera.cairn.application.tasks;

import io.ciera.cairn.application.TaskPriority;

public abstract class GeneratedEventToSelfTask extends GeneratedEventTask {
	
	@Override
	public int getPriority() {
		return TaskPriority.GENERATED_EVENT_TO_SELF_PRIORITY;
	}

}
