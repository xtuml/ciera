package io.ciera.summit.statemachine;

public abstract class ClassStateMachine extends StateMachine implements EventTarget {
    
    private String keyLetters;
    
    public ClassStateMachine( String keyLetters ) {
        this.keyLetters = keyLetters;
    }
    
    @Override
    public String getKeyLetters() {
        return keyLetters;
    }
    
}
