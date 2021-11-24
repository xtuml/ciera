package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Door;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.impl.OvenImpl;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.ITransition;
import io.ciera.runtime.summit.statemachine.StateMachine;


public class DoorStateMachine extends StateMachine<Door,MicrowaveOven> {

    public static final int Closed = 1;
    public static final int Open = 0;


    private Door self;

    public DoorStateMachine(Door self, MicrowaveOven context) {
        this(self, context, Open);
    }

    public DoorStateMachine(Door self, MicrowaveOven context, int initialState) {
        super(context, initialState);
        this.self = self;
    }

    private void Closed_entry_action() throws XtumlException {
        self().setIs_secure(true);
    }

    private void Open_entry_action() throws XtumlException {
        self().setIs_secure(false);
        Oven oven = self().R4_provides_access_to_Oven();
        context().generate(new OvenImpl.cancel_cooking(getRunContext(), context().getId()).to(oven));
    }



    private void Closed_release_txn_to_Open_action() throws XtumlException {
    }

    private void Open_close_txn_to_Closed_action() throws XtumlException {
    }



    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
            { IGNORE,
              (event) -> {Open_close_txn_to_Closed_action();Closed_entry_action();return Closed;}
            },
            { (event) -> {Closed_release_txn_to_Open_action();Open_entry_action();return Open;},
              IGNORE
            }
        };
    }

    @Override
    public Door self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "Door";
    }

}
