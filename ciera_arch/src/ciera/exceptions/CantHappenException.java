package ciera.exceptions;

@SuppressWarnings("serial")
public class CantHappenException extends StateMachineException {
    public CantHappenException( String message ) {
        super( message );
    }
}
