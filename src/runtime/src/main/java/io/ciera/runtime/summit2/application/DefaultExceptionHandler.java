package io.ciera.runtime.summit2.application;

public class DefaultExceptionHandler implements ExceptionHandler{

    @Override
    public void handle(RuntimeException e) {
        // TODO
        e.printStackTrace();
    }

}
