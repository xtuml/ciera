package io.ciera.runtime.application;

public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(RuntimeException e) {
        // TODO
        e.printStackTrace();
        System.exit(1);
    }

}