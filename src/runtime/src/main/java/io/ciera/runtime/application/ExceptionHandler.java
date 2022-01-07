package io.ciera.runtime.application;

public interface ExceptionHandler {

    public void handleError(RuntimeException e);

    public void handleSoftError(RuntimeException e);

}
