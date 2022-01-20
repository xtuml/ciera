package io.ciera.runtime.application.task;

public class Halt extends Task {

    @Override
    public void run() {
        getContext().getApplication().stop();
    }

}
