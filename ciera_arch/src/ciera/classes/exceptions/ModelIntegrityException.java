package ciera.classes.exceptions;

@SuppressWarnings("serial")
public class ModelIntegrityException extends Exception {
    public ModelIntegrityException( String message ) {
        super( message );
    }
}
