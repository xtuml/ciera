package io.ciera.runtime.application.task;

public class IdleHalt extends Halt {

    @Override
    public int getPriority() {
        return Task.DEFAULT_PRIORITY - 1;
    }

}
