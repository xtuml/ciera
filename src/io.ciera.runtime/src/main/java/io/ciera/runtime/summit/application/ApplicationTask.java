package io.ciera.runtime.summit.application;

public abstract class ApplicationTask implements IApplicationTask {

    private static int systemSequenceNumber = 0;

    private int sequenceNumber;

    public ApplicationTask() {
        sequenceNumber = systemSequenceNumber++;
    }

    @Override
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public int compareTo(IApplicationTask task) {
        if (getPriority() == task.getPriority())
            return getSequenceNumber() - task.getSequenceNumber();
        else
            return getPriority() - task.getPriority();
    }

}
