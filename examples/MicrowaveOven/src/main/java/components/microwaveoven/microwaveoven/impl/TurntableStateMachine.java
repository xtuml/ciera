package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Turntable;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.ITransition;
import io.ciera.runtime.summit.statemachine.StateMachine;


public class TurntableStateMachine extends StateMachine<Turntable,MicrowaveOven> {

    public static final int Rotating = 1;
    public static final int Stationary = 0;


    private Turntable self;

    public TurntableStateMachine(Turntable self, MicrowaveOven context) {
        this(self, context, Stationary);
    }

    public TurntableStateMachine(Turntable self, MicrowaveOven context, int initialState) {
        super(context, initialState);
        this.self = self;
    }

    private void Rotating_entry_action() throws XtumlException {
    }

    private void Stationary_entry_action() throws XtumlException {
    }



    private void Rotating_stop_txn_to_Stationary_action() throws XtumlException {
    }

    private void Stationary_spin_txn_to_Rotating_action() throws XtumlException {
    }



    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
            { (event) -> {Stationary_spin_txn_to_Rotating_action();Rotating_entry_action();return Rotating;},
              IGNORE
            },
            { IGNORE,
              (event) -> {Rotating_stop_txn_to_Stationary_action();Stationary_entry_action();return Stationary;}
            }
        };
    }

    @Override
    public Turntable self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "Turntable";
    }

}
