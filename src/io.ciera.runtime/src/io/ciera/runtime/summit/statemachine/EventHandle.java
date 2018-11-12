package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

public final class EventHandle extends UniqueId {
	
	public EventHandle() {}
	
	public EventHandle(UniqueId id) {
		super(id);
	}
	
	public static EventHandle random() {
        return new EventHandle(UniqueId.random());
	}
	
	public static EventHandle deserialize(Object o) throws XtumlException {
		return new EventHandle(UniqueId.deserialize(o));
	}

}
