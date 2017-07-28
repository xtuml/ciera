package ciera.statemachine;

public abstract class AssignerStateMachine extends StateMachine implements EventTarget {
    
    private String keyLetters;
    
    public AssignerStateMachine( String keyLetters ) {
        this.keyLetters = keyLetters;
    }
    
    @Override
    public String getKeyLetters() {
        return keyLetters;
    }
    
}
