package io.ciera.cairn.application.tasks;

import io.ciera.cairn.application.ApplicationTask;
import io.ciera.cairn.application.TaskPriority;

//import io.ciera.summit.time.Timer;

public abstract class PoppedTimerTask extends ApplicationTask {

	//public abstract Timer getTimer();

	@Override
	public int getPriority() {
		return TaskPriority.POPPED_TIMER_PRIORITY;
	}

}
