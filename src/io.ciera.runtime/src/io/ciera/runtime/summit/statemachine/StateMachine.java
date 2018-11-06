package io.ciera.runtime.summit.statemachine;

import io.ciera.runtime.summit.application.IInstanceActionHome;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.IComponent;

public abstract class StateMachine<T extends IModelInstance<T,C>, C extends IComponent<C>> implements IStateMachine<T,C>, IInstanceActionHome<T,C> {
	
	public static final int NON_EXISTENT = -1;

	private int currentState;
	private C context;
	
	public StateMachine(C context) {
		currentState = NON_EXISTENT;
		this.context = context;
	}

	@Override
	public int getCurrentState() {
		return currentState;
	}

	@Override
	public void setCurrentState(int state) {
		currentState = state;
	}

	@Override
	public IRunContext getRunContext() {
		return context().getRunContext();
	}

	@Override
	public C context() {
		return context;
	}

}
