package io.ciera.runtime.instanceloading;

public class AttributeChangedDelta extends ModelDelta implements IAttributeChangedDelta {
	
	private String attributeName;
	private Object oldValue;
	private Object newValue;

	public AttributeChangedDelta(Object modelElement, String elementName, String attributeName, Object oldValue, Object newValue) {
		super(modelElement, elementName);
		this.attributeName = attributeName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public AttributeChangedDelta(IAttributeChangedDelta delta1, IAttributeChangedDelta delta2) {
		super(delta1);
		attributeName = delta1.getAttributeName();
		oldValue = delta1.getOldValue();
		newValue = delta2.getNewValue();
	}

	@Override
	public String getAttributeName() {
		return attributeName;
	}

	@Override
	public Object getOldValue() {
		return oldValue;
	}

	@Override
	public Object getNewValue() {
		return newValue;
	}
}
