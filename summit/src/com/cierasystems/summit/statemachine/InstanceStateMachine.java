package com.cierasystems.summit.statemachine;

import com.cierasystems.summit.classes.IModelInstance;

public abstract class InstanceStateMachine extends StateMachine {

    private IModelInstance instance;
    
    public InstanceStateMachine() {
        instance = null;
    }
    
    public void setInstance( IModelInstance instance ) {
        if ( null == this.instance ) this.instance = instance;
    }
    
    public IModelInstance getInstance() {
        return instance;
    }

    @Override
    public String getKeyLetters() {
        return instance.getKeyLetters();
    }
    
}
