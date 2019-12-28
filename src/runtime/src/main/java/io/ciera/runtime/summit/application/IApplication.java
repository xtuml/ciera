package io.ciera.runtime.summit.application;

public interface IApplication {
    
    public void setup(String[] args, ILogger logger);

    default public void setup(String[] args) {
        setup(args, null);
    }

    default public void setup() {
        setup(new String[0]);
    }

    public void initialize();

    public void start();
    
    public void stop();

    public void printVersions();

}
