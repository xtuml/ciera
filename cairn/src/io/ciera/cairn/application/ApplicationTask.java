package io.ciera.cairn.application;

import io.ciera.summit.application.IApplicationTask;

public abstract class ApplicationTask implements IApplicationTask {

	@Override
	public int compareTo( IApplicationTask task ) {
		return getPriority() - task.getPriority();
	}

}
