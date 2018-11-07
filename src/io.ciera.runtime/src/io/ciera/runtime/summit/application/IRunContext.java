package io.ciera.runtime.summit.application;

import io.ciera.runtime.summit.time.Timer;

public interface IRunContext {

    public void start();
    public void execute(IApplicationTask task);
    public IExceptionHandler getExceptionHandler();
    public void setExceptionHandler(IExceptionHandler h);
    public String[] args();
    public void addTimer(Timer timer);
    public boolean cancelTimer(Timer timer);
    
}
