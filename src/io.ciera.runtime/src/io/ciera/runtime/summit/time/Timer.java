package io.ciera.runtime.summit.time;

import io.ciera.runtime.summit.statemachine.IEvent;

public class Timer implements Comparable<Timer> {

    private IEvent eventToGenerate;
    private long wakeUpTime; // time to wake up in milliseconds since the epoch
    private boolean recurring;
    private int period; // duration in millizeconds
    
    public Timer( IEvent e, int microseconds, boolean recur ) {
        eventToGenerate = e;
        recurring = recur;
        period = microseconds / 1000;
        calculateWakeUpTime();
    }
     
    public IEvent getEventToGenerate() {
        return eventToGenerate;
    }
     
    public long getWakeUpTime() {
        return wakeUpTime;
    }
     
    public void calculateWakeUpTime() {
        wakeUpTime = System.currentTimeMillis() + period;
    }
     
    public void setPeriod( int microseconds ) {
        period = microseconds / 1000;
    }
    
    public void reset() {
    	wakeUpTime += period;
    }
     
    public boolean isRecurring() {
        return recurring;
    }
     
    @Override
    public int compareTo( Timer o ) { 
        if ( getWakeUpTime() < o.getWakeUpTime() ) return -1;
        else if ( getWakeUpTime() > o.getWakeUpTime() ) return 1;
        else return 0;
    }
     
}
