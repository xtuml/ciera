package io.ciera.runtime.summit.application;

import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.statemachine.EventSet;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.time.Timer;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.time.TimerSet;

public interface IRunContext {

    public void start();

    public void execute(IApplicationTask task);

    public void performTransaction(IApplicationTask task);
    
    public void heartbeat();

    public IExceptionHandler getExceptionHandler();

    public void setExceptionHandler(IExceptionHandler h);

    public String[] args();

    public TimerHandle addTimer(Timer timer);

    public boolean cancelTimer(TimerHandle t);
    
    public TimerSet getActiveTimers();
    
    public Timer getTimer(TimerHandle t);
    
    public void registerEvent(IEvent event);

    public void deregisterEvent(EventHandle e);

    public EventSet getActiveEvents();
    
    public IEvent getEvent(EventHandle e);

    public long time();

}
