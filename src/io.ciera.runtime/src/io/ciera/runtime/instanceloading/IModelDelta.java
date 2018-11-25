package io.ciera.runtime.instanceloading;

import io.ciera.runtime.summit.types.UniqueId;

public interface IModelDelta {
	
	public Object getModelElement();
	public String getElementName();
	public UniqueId getElementId();

}
