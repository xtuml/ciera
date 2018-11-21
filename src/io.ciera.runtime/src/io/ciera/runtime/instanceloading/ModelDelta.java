package io.ciera.runtime.instanceloading;

public class ModelDelta implements IModelDelta {
	
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

}
