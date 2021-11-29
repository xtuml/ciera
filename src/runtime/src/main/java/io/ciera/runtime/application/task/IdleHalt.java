package io.ciera.runtime.application.task;

import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Task;

public class IdleHalt extends Halt {

    public IdleHalt(ExecutionContext context) {
        super(context);
    }

    @Override
    public int getPriority() {
        return Task.DEFAULT_PRIORITY - 1;
    }

}
