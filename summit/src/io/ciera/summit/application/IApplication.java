package io.ciera.summit.application;

public interface IApplication {

    public void setup( String[] args );
    default public void setup() {
        setup( new String[0] );
    }
    public void initialize();
    public void start();

}
