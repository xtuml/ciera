package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.InternalLight;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.ITransition;
import io.ciera.runtime.summit.statemachine.StateMachine;


public class InternalLightStateMachine extends StateMachine<InternalLight,MicrowaveOven> {

    public static final int Off = 0;
    public static final int On = 1;


    private InternalLight self;

    public InternalLightStateMachine(InternalLight self, MicrowaveOven context) {
        this(self, context, Off);
    }

    public InternalLightStateMachine(InternalLight self, MicrowaveOven context, int initialState) {
        super(context, initialState);
        this.self = self;
    }

    private void Off_entry_action() throws XtumlException {
    }

    private void On_entry_action() throws XtumlException {
    }



    private void Off_switch_on_txn_to_On_action() throws XtumlException {
    }

    private void On_switch_off_txn_to_Off_action() throws XtumlException {
    }



    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
            { IGNORE,
              (event) -> {Off_switch_on_txn_to_On_action();On_entry_action();return On;}
            },
            { (event) -> {On_switch_off_txn_to_Off_action();Off_entry_action();return Off;},
              IGNORE
            }
        };
    }

    @Override
    public InternalLight self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "InternalLight";
    }

}
