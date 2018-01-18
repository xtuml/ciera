package io.ciera.summit.exceptions;

@SuppressWarnings("serial")
public class BadArgumentException extends XtumlException {
    public BadArgumentException( String message ) {
        super( message );
    }
}
