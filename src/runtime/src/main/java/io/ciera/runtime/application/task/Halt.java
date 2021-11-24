package io.ciera.runtime.application.task;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Task;

public class Halt extends Task {

    public Halt(ExecutionContext context) {
        super(context);
    }

    @Override
    public void run() {
        getContext().getApplication().stop();
    }

}
