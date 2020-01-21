package io.ciera.runtime.summit.types;

import java.time.Duration;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.exceptions.XtumlException;

public class TimeStamp implements IXtumlType, Comparable<TimeStamp> {

    private long timestamp;

    public TimeStamp() {
        timestamp = 0;
    }

    public TimeStamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public TimeStamp subtract(TimeStamp t) throws XtumlException {
        if (null == t)
            return null;
        else if (timestamp < t.timestamp)
            throw new XtumlException("Negative timestamp");
        else
            return new TimeStamp(timestamp - t.timestamp);
    }

    @Override
    public int compareTo(TimeStamp o) {
        return timestamp == o.timestamp ? 0 : timestamp < o.timestamp ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TimeStamp && timestamp == ((TimeStamp) o).timestamp;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(timestamp);
    }

    @Override
    public String toString() {
        return Duration.ofMillis(timestamp).toString();
    }

    public static TimeStamp now(IRunContext runContext) {
        return new TimeStamp(runContext.time() / 1000L);
    }

	@Override
	public String serialize() {
		return Long.toString(timestamp);
	}
	
	public static TimeStamp deserialize(Object o) throws XtumlException {
		if (o instanceof TimeStamp) {
            return (TimeStamp)o;
		}
		else if (o instanceof Long) {
			return new TimeStamp(((Long)o).longValue());
		}
		else if (o instanceof Integer) {
			return new TimeStamp(((Integer)o).longValue());
		}
		else if (o instanceof String) {
			return new TimeStamp(Long.parseLong((String)o));
		}
		else throw new XtumlException("Cannot deserialize value");
	}

}
