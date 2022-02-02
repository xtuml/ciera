package io.ciera.runtime.domain;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.DynamicObjectInstance;
import io.ciera.runtime.api.domain.StateMachine;
import io.ciera.runtime.api.exceptions.DeletedInstanceException;
import io.ciera.runtime.api.exceptions.EmptyInstanceException;
import io.ciera.runtime.api.exceptions.EventTargetException;
import io.ciera.runtime.api.types.UniqueId;

public abstract class AbstractDynamicObjectInstance extends AbstractObjectInstance implements DynamicObjectInstance {

	private Enum<?> currentState;
	private StateMachine stateMachine;

	public AbstractDynamicObjectInstance() {
		super();
	}

	public AbstractDynamicObjectInstance(Domain domain) {
		super(domain);
		this.stateMachine = null;
	}

	public AbstractDynamicObjectInstance(UniqueId instanceId, Domain domain) {
		super(instanceId, domain);
		this.stateMachine = null;
	}

	public void setStateMachine(StateMachine stateMachine) {
		if (this.stateMachine == null) {
			this.stateMachine = stateMachine;
		}
	}

	@Override
	public Enum<?> getCurrentState() {
		return currentState;
	}

	@Override
	public void setCurrentState(Enum<?> newState) {
		currentState = newState;
	}

	@Override
	public void consumeEvent(Event event) {
		if (isAlive()) {
			if (!isEmpty()) {
				if (stateMachine != null) {
					if (event != null) {
						stateMachine.consumeEvent(event);
						getSubtypeInstances().stream().forEach(o -> o.consumeEvent(event));
					} else {
						throw new EventTargetException("Cannot consume null event", this, event);
					}
				} else {
					throw new IllegalStateException("Dynamic instance has no state machine");
				}
			} else {
				throw new EventTargetException("Failed to deliver event",
						new EmptyInstanceException("Empty instance cannot process event", getDomain(), this), this,
						event);
			}
		} else {
			throw new EventTargetException("Failed to deliver event",
					new DeletedInstanceException("Cannot deliver event to deleted instance", getDomain(), this), this,
					event);
		}
	}
}
