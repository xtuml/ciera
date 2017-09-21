package com.cierasystems.summit.exceptions;

@SuppressWarnings("serial")
public class SameDataException extends StateMachineException {
    public SameDataException( String message ) {
        super( message );
    }
}
