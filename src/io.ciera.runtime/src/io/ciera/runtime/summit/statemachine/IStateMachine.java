package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IStateMachine<T extends IModelInstance<T,C>, C extends IComponent<C>> extends IEventTarget {
	
	public int getCurrentState();
	public void setCurrentState(int state);
	public void transition(IEvent event) throws XtumlException;
	public T self();

}
