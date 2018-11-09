package io.ciera.runtime.summit.time;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.ciera.runtime.summit.types.Set;

public class TimerSet extends Set<TimerSet, Timer> {
	
	public TimerSet() {
	}

	public TimerSet(Collection<Timer> c) {
		super(c);
	}

	@Override
	public Timer nullElement() {
		return null;
	}

	@Override
	public TimerSet emptySet() {
		return new TimerSet();
	}

	@Override
	public List<Timer> elements() {
        return Arrays.asList(toArray(new Timer[0]));
	}

	@Override
	public TimerSet value() {
		return this;
	}

}
