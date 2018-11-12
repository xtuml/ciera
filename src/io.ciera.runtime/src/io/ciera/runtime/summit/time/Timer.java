package io.ciera.runtime.summit.time;

import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.types.IXtumlType;

public class Timer implements Comparable<Timer>, IXtumlType<Timer> {

	private TimerHandle timerId;
    private EventHandle eventToGenerate;
    private int wakeUpTime; // time to wake up in microseconds since the start of the application
    private int period;     // duration in microseconds
    private boolean recurring;

    public Timer(EventHandle e, int microseconds, boolean recur) {
    	timerId = TimerHandle.random();
        eventToGenerate = e;
        recurring = recur;
        period = microseconds;
    }

    public EventHandle getEventToGenerate() {
        return eventToGenerate;
    }

    public int getWakeUpTime() {
        return wakeUpTime;
    }

    public void addTime(int microseconds) {
        wakeUpTime += microseconds;
    }

    public void setPeriod(int microseconds) {
        period = microseconds;
    }

    public void reset(int currentTimeMicro) {
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
