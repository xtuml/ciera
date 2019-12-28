package io.ciera.runtime.summit.application;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IExceptionHandler {

    public void handle(XtumlException e);

    public void warn(String message);

}
