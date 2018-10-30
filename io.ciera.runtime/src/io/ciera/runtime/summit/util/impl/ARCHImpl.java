package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.util.ARCH;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.summit.components.IComponent;

public class ARCHImpl<C extends IComponent<C>> extends Utility<C> implements ARCH {

    public ARCHImpl( C context ) {
        super( context );
    }

    @Override
    public void shutdown() {
        getRunContext().execute( new HaltExecutionTask() );
    }

}
