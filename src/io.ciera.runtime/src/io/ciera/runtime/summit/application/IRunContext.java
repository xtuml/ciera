package io.ciera.runtime.summit.application;

import io.ciera.runtime.summit.statemachine.EventSet;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.time.Timer;
import io.ciera.runtime.summit.time.TimerSet;

public interface IRunContext {

    public void start();

    public void execute(IApplicationTask task);

    public IExceptionHandler getExceptionHandler();

    public void setExceptionHandler(IExceptionHandler h);

    public String[] args();

    public void addTimer(Timer timer);

    public boolean cancelTimer(Timer timer);
    
    public TimerSet getActiveTimers();
    
    public void registerEvent(IEvent event);

    public boolean deregisterEvent(IEvent event);

    public EventSet getActiveEvents();

    public long time();

}
