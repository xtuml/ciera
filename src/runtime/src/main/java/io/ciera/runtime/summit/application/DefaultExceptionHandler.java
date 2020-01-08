package io.ciera.runtime.summit.application;

import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.exceptions.XtumlInterruptedException;

public class DefaultExceptionHandler implements IExceptionHandler {
    
    private IRunContext runContext;
    
    public DefaultExceptionHandler(IRunContext runContext) {
        this.runContext = runContext;
    }

    @Override
    public void handle(XtumlException e) {
        if (e instanceof XtumlInterruptedException) {
            // Exit normally
            runContext.execute(new HaltExecutionTask());
        } else {
            runContext.getLog().error(e);
            System.exit(1);
        }
    }

    @Override
    public void warn(String message) {
        runContext.getLog().warn(message);
    }

}
