package io.ciera.runtime.summit.time;

import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.types.IXtumlType;

public class Timer implements Comparable<Timer>, IXtumlType<Timer> {

	private TimerHandle timerId;
    private EventHandle eventToGenerate;
    private long wakeUpTime;   // time to wake up in microseconds since the epoch
    private int period;        // duration in microseconds
    private boolean recurring;

    public Timer(EventHandle e, int microseconds, boolean recur) {
    	timerId = TimerHandle.random();
        eventToGenerate = e;
        recurring = recur;
        period = microseconds;
    }

    public Timer(TimerHandle id, EventHandle e, long wakeUpTime, int period, boolean recur) {
    	timerId = id;
    	eventToGenerate = e;
    	this.wakeUpTime = wakeUpTime;
    	this.period = period;
    	recurring = recur;
    }

    public EventHandle getEventToGenerate() {
        return eventToGenerate;
    }

    public long getWakeUpTime() {
        return wakeUpTime;
    }

    public void addTime(int microseconds) {
        wakeUpTime += microseconds;
    }

    public void setPeriod(int microseconds) {
        period = microseconds;
    }
    
    public int getPeriod() {
    	return period;
    }

    public void reset(long currentTimeMicro) {
        wakeUpTime = currentTimeMicro + period;
    }

    public boolean isRecurring() {
        return recurring;
    }
    
    public TimerHandle getId() {
    	return timerId;
    }

    @Override
    public int compareTo(Timer o) {
        if (getWakeUpTime() < o.getWakeUpTime())
            return -1;
        else if (getWakeUpTime() > o.getWakeUpTime())
            return 1;
        else
            return 0;
    }

    @Override
    public Timer value() {
        return this;
    }

}
