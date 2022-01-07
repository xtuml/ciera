package io.ciera.runtime.application;

public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public void handleError(RuntimeException e) {
        e.printStackTrace();
        System.exit(1);
    }

    @Override
    public void handleSoftError(RuntimeException e) {
        throw e;
    }

}
