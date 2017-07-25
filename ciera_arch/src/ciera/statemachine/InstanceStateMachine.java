package ciera.statemachine;

import ciera.classes.ModelInstance;

public abstract class InstanceStateMachine extends StateMachine {

    private ModelInstance instance;
    
    public InstanceStateMachine() {
        instance = null;
    }
    
    public void setInstance( ModelInstance instance ) {
        if ( null == this.instance ) this.instance = instance;
    }
    
    public ModelInstance getInstance() {
        return instance;
    }
    
}
