package io.ciera.runtime.instanceloading;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.time.Timer;
import io.ciera.runtime.summit.types.UniqueId;

public abstract class ModelDelta implements IModelDelta {
	
	private Object modelElement;
	private String elementName;
	
	public ModelDelta(Object modelElement, String elementName) {
		this.modelElement = modelElement;
		this.elementName = elementName;
	}

	public ModelDelta(IModelDelta delta) {
		modelElement = delta.getModelElement();
		elementName = delta.getElementName();
	}

	@Override
	public Object getModelElement() {
		return modelElement;
	}

	@Override
	public String getElementName() {
		return elementName;
	}
	
	@Override
	public UniqueId getElementId() {
		if (modelElement instanceof IModelInstance<?,?>) {
			return ((IModelInstance<?,?>)modelElement).getInstanceId();
		}
		else if (modelElement instanceof IEvent) {
			return ((IEvent)modelElement).getEventHandle();
		}
		else if (modelElement instanceof Timer) {
			return ((Timer)modelElement).getId();
		}
		else {
			return new UniqueId();
		}
	}

}
