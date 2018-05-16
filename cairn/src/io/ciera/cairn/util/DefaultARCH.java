package io.ciera.cairn.util;

import io.ciera.cairn.application.tasks.HaltExecutionTask;
import io.ciera.summit.components.IComponent;

public final class DefaultARCH {

    public static void shutdown( IComponent<?> context ) {
    	context.getRunContext().execute( new HaltExecutionTask() );
    }

}
