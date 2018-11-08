package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IStateMachine<T extends IModelInstance<T,C>, C extends IComponent<C>> {
	
	public String getClassName();
	public String getStateName(int state);
	public ITransition[][] getStateEventMatrix();
	public void transition(IEvent event) throws XtumlException;

}
