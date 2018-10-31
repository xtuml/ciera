package io.ciera.runtime.summit.application;

public abstract class ApplicationTask implements IApplicationTask {

    @Override
    public int compareTo( IApplicationTask task ) {
        return getPriority() - task.getPriority();
    }

}
