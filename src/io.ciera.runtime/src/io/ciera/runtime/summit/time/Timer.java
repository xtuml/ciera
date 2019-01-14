package io.ciera.runtime.summit.time;

import io.ciera.runtime.instanceloading.AttributeChangedDelta;
import io.ciera.runtime.instanceloading.InstanceCreatedDelta;
import io.ciera.runtime.instanceloading.InstanceDeletedDelta;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.types.IXtumlType;

public class Timer implements Comparable<Timer>, IXtumlType<Timer> {

	private TimerHandle timerId;
	private int populationId;
    private EventHandle eventToGenerate;
    private long wakeUpTime;   // time to wake up in microseconds since the epoch
    private int period;        // duration in microseconds
    private boolean recurring;

    public Timer(int populationId, EventHandle e, int microseconds, boolean recur) {
    	timerId = TimerHandle.random();
    	this.populationId = populationId;
        eventToGenerate = e;
        recurring = recur;
        period = microseconds;
    }

    public Timer(TimerHandle id, int populationId, EventHandle e, long wakeUpTime, int period, boolean recur) {
    	timerId = id;
    	this.populationId = populationId;
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
    
    public int getPopulationId() {
    	return populationId;
    }

    @Override
    public int compareTo(Timer o) {
        if (getWakeUpTime() < o.getWakeUpTime())
            return -1;
        else if (getWakeUpTime() > o.getWakeUpTime())
            return 1;
        else
            return timerId.compareTo(o.getId());
    }

    @Override
    public Timer value() {
        return this;
    }
    
    @Override
    public boolean equals(Object o) {
    	return null != o && null != timerId && timerId.equals(((Timer)o).getId());
    }
    
    @Override
    public int hashCode() {
    	return timerId.hashCode();
    }
    
    public static class TimerCreatedDelta extends InstanceCreatedDelta {
    	public TimerCreatedDelta(Timer t) {
    		super(t, "CIERA_TIMER");
    	}
    }

    public static class TimerDeletedDelta extends InstanceDeletedDelta {
    	public TimerDeletedDelta(Timer t) {
    		super(t, "CIERA_TIMER");
    	}
    }

    public static class TimerAttributeChangedDelta extends AttributeChangedDelta {
    	public TimerAttributeChangedDelta(Timer t, String attributeName, Object oldValue, Object newValue) {
    		super(t, "CIERA_TIMER", attributeName, oldValue, newValue);
    	}
    }

}
