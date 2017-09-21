package ciera.exceptions;

@SuppressWarnings("serial")
public class DeletedInstanceException extends XtumlException {
    public DeletedInstanceException( String message ) {
        super( message );
    }
}
