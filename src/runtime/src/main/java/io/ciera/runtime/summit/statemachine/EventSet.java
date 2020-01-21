package io.ciera.runtime.summit.statemachine;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import io.ciera.runtime.summit.types.Set;

public class EventSet extends Set<IEvent> {
	
	public EventSet() {
	}

    public EventSet(Comparator<? super IEvent> comp) {
        super(comp);
    }

	public EventSet(Collection<IEvent> c) {
		super(c);
	}

	@Override
	public IEvent nullElement() {
		return null;
	}

	@Override
	public EventSet emptySet() {
		return new EventSet();
	}

	@Override
	public EventSet emptySet(Comparator<? super IEvent> comp) {
		return new EventSet(comp);
	}

	@Override
	public List<IEvent> elements() {
        return Arrays.asList(toArray(new IEvent[0]));
	}

}
