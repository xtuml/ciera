package io.ciera.cairn.util;

import io.ciera.summit.application.IActionHome;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.components.IComponent;

public class Utility<C extends IComponent<C>> implements IActionHome<C> {
	
	C population;
	
	public Utility( C population ) {
		this.population = population;
	}

	@Override
	public IRunContext getRunContext() {
		return population().getRunContext();
	}

	@Override
	public C population() {
		return population;
	}

}
