package io.ciera.runtime.summit.time;

import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.UniqueId;

public class Timer implements Comparable<Timer>, IXtumlType<Timer> {

	private UniqueId timerId;
    private IEvent eventToGenerate;
    private long wakeUpTime; // time to wake up in microseconds since the epoch
    private long period; // duration in microseconds
    private boolean recurring;

    public Timer(IEvent e, int microseconds, boolean recur) {
    	timerId = UniqueId.random();
        eventToGenerate = e;
        recurring = recur;
        period = microseconds;
    }

    public IEvent getEventToGenerate() {
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

    public void reset(long currentTimeMicro) {
        wakeUpTime = currentTimeMicro + period;
    }

    public boolean isRecurring() {
        return recurring;
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

	@Override
	public String serialize() {
		return "\"" + timerId.toString() + "\"";
	}

}
