package io.ciera.runtime.instanceloading;

public class InstanceDeletedDelta extends ModelDelta implements IInstanceDeletedDelta {
	public InstanceDeletedDelta(Object modelElement, String elementName) {
		super(modelElement, elementName);
	}
}
