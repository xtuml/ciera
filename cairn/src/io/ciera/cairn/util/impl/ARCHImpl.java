package io.ciera.cairn.util.impl;

import io.ciera.cairn.application.tasks.HaltExecutionTask;
import io.ciera.cairn.util.ARCH;
import io.ciera.cairn.util.Utility;
import io.ciera.summit.components.IComponent;

public class ARCHImpl<C extends IComponent<C>> extends Utility<C> implements ARCH {

    public ARCHImpl( C population ) {
		super( population );
	}

	@Override
	public void shutdown() {
    	getRunContext().execute( new HaltExecutionTask() );
	}

}
