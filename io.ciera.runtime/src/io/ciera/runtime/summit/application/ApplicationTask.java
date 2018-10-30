package io.ciera.runtime.summit.application;

import io.ciera.summit.application.IApplicationTask;

public abstract class ApplicationTask implements IApplicationTask {

    @Override
    public int compareTo( IApplicationTask task ) {
        return getPriority() - task.getPriority();
    }

}
