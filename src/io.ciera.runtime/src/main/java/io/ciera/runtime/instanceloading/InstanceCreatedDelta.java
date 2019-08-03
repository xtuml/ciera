package io.ciera.runtime.instanceloading;

public class InstanceCreatedDelta extends ModelDelta implements IInstanceCreatedDelta {
	public InstanceCreatedDelta(Object modelElement, String elementName) {
		super(modelElement, elementName);
	}
}
