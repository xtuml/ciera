package io.ciera.runtime.application.task;

public class GenericTask extends Task {

    private static final long serialVersionUID = 1L;

    private final transient Runnable command;

    public GenericTask(Runnable command) {
        this.command = command;
    }

    @Override
    public void run() {
        if (command != null) {
            command.run();
        }
    }

}