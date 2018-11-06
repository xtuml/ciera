package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.exceptions.StateMachineException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public class Event implements IEvent {
	
	private Object[] dataItems;
	
	public Event(Object ... dataItems) {
		this.dataItems = dataItems;
	}

	@Override
	public Object get(int index) throws XtumlException {
		if (index >= 0 && index < dataItems.length) {
			return dataItems[index];
		}
		else throw new StateMachineException("Invalid index");
	}

}
