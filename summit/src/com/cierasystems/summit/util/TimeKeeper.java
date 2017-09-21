package com.cierasystems.summit.util;

import com.cierasystems.summit.statemachine.Event;

public interface TimeKeeper {
    
    public void addTime( Timer t, int microseconds );
    public int getRemainingTime( Timer t );
    public void resetTime( Timer t, int microseconds );
    public void cancel( Timer t );
    public Timer newTimer( Event e, int microseconds, boolean recurring );
    public long currentTimeMicro();
    
}
