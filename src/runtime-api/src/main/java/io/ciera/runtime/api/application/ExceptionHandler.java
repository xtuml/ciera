package io.ciera.runtime.api.application;

public interface ExceptionHandler {

    public void handleError(RuntimeException e);

    public void handleSoftError(RuntimeException e);

}
