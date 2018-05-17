package io.ciera.summit.application;

import io.ciera.summit.components.IComponent;

public interface IActionHome<C extends IComponent<C>> {
	
	public IRunContext getRunContext();
	public C population();

}
