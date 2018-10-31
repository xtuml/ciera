package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.ARCH;
import io.ciera.runtime.summit.util.Utility;

public class ARCHImpl<C extends IComponent<C>> extends Utility<C> implements ARCH {

    public ARCHImpl( C context ) {
        super( context );
    }

    @Override
    public void shutdown() {
        getRunContext().execute( new HaltExecutionTask() );
    }

}
