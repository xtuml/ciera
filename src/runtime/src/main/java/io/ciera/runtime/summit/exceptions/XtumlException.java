package io.ciera.runtime.summit.exceptions;

@SuppressWarnings("serial")
public class XtumlException extends Exception {

    public XtumlException(String message) {
        super(message);
    }

    public XtumlException(Throwable t) {
        super(t);
    }

    public XtumlException(String message, Throwable t) {
        super(message, t);
    }
}
