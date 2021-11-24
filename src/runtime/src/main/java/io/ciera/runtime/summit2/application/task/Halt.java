package io.ciera.runtime.summit2.application.task;

import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Task;

public class Halt extends Task {

    public Halt(ExecutionContext context) {
        super(context);
    }

    @Override
    public void run() {
        getContext().getApplication().stop();
    }
    
}
