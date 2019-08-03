package io.ciera.runtime.instanceloading;

public interface IAttributeChangedDelta extends IModelDelta {
	
	public String getAttributeName();
	public Object getOldValue();
	public Object getNewValue();

}
