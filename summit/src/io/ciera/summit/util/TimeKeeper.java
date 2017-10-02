package io.ciera.summit.util;

import io.ciera.summit.statemachine.Event;

public interface TimeKeeper {
    
    public void addTime( Timer t, int microseconds );
    public int getRemainingTime( Timer t );
    public void resetTime( Timer t, int microseconds );
    public void cancel( Timer t );
    public Timer newTimer( Event e, int microseconds, boolean recurring );
    public long currentTimeMicro();
    
}
