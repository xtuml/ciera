package io.ciera.runtime.summit2.application;

public abstract class Application {
    
    private boolean running;

    public abstract Logger getLogger();
    public boolean isRunning() {
        return running;
    }
    public synchronized void stop() {
        running = false;
    }

}
