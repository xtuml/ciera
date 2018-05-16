package io.ciera.summit.application;

import io.ciera.summit.components.IComponent;

public interface IActionHome {
	
	public IRunContext getRunContext();
	public <E extends IComponent> E getPopulationContext();

}
