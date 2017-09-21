package ciera.util;

import ciera.application.XtumlApplication;
import ciera.statemachine.Event;

public class Timer implements Comparable<Timer> {
    
    private Event eventToGenerate;
    private long wakeUpTime;            // time to wake up in microseconds since the epoch
    private boolean recurring;
    private int period;
    
    public Timer( Event e, int microseconds, boolean recur ) {
        eventToGenerate = e;
        recurring = recur;
        setPeriod( microseconds );
        calculateWakeUpTime();
    }

    public Event getEventToGenerate() {
        return eventToGenerate;
    }

    public long getWakeUpTime() {
        return wakeUpTime;
    }
    
    public void calculateWakeUpTime() {
        wakeUpTime = XtumlApplication.app.getTimeKeeper().currentTimeMicro() + period;
    }
    
    public void addToWakeUpTime( int microseconds ) {
        wakeUpTime += microseconds;
    }
    
    public void setPeriod( int microseconds ) {
        period = microseconds;
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
