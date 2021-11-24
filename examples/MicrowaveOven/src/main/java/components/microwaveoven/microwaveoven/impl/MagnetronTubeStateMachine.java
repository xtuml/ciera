package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.datatypes.Tube_Wattage;
import components.microwaveoven.microwaveoven.MagnetronTube;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.ITransition;
import io.ciera.runtime.summit.statemachine.StateMachine;


public class MagnetronTubeStateMachine extends StateMachine<MagnetronTube,MicrowaveOven> {

    public static final int Output_Power_Stable_and_OFF = 0;
    public static final int Output_Power_Stable_and_ON = 1;
    public static final int Raising_Output_Power = 2;
    public static final int Reducing_Output_Power = 3;


    private MagnetronTube self;

    public MagnetronTubeStateMachine(MagnetronTube self, MicrowaveOven context) {
        this(self, context, Output_Power_Stable_and_OFF);
    }

    public MagnetronTubeStateMachine(MagnetronTube self, MicrowaveOven context, int initialState) {
        super(context, initialState);
        this.self = self;
    }

    private void Output_Power_Stable_and_OFF_entry_action() throws XtumlException {
    }

    private void Output_Power_Stable_and_ON_entry_action() throws XtumlException {
    }

    private void Raising_Output_Power_entry_action() throws XtumlException {
        if ( self().getCurrent_power_output().equality(Tube_Wattage.LOW) ) {
            self().setCurrent_power_output(Tube_Wattage.MED_LOW);
        }
        else if ( self().getCurrent_power_output().equality(Tube_Wattage.MED_LOW) ) {
            self().setCurrent_power_output(Tube_Wattage.MEDIUM);
        }
        else if ( self().getCurrent_power_output().equality(Tube_Wattage.MEDIUM) ) {
            self().setCurrent_power_output(Tube_Wattage.MED_HIGH);
        }
        else if ( self().getCurrent_power_output().equality(Tube_Wattage.MED_HIGH) ) {
            self().setCurrent_power_output(Tube_Wattage.HIGH);
        }
    }

    private void Reducing_Output_Power_entry_action() throws XtumlException {
        if ( self().getCurrent_power_output().equality(Tube_Wattage.MED_LOW) ) {
            self().setCurrent_power_output(Tube_Wattage.LOW);
        }
        else if ( self().getCurrent_power_output().equality(Tube_Wattage.MEDIUM) ) {
            self().setCurrent_power_output(Tube_Wattage.MED_LOW);
        }
        else if ( self().getCurrent_power_output().equality(Tube_Wattage.MED_HIGH) ) {
            self().setCurrent_power_output(Tube_Wattage.MEDIUM);
        }
        else if ( self().getCurrent_power_output().equality(Tube_Wattage.HIGH) ) {
            self().setCurrent_power_output(Tube_Wattage.MED_HIGH);
        }
    }



    private void Output_Power_Stable_and_OFF_power_on_txn_to_Output_Power_Stable_and_ON_action() throws XtumlException {
    }

    private void Output_Power_Stable_and_OFF_increase_power_txn_to_Raising_Output_Power_action() throws XtumlException {
    }

    private void Output_Power_Stable_and_OFF_decrease_power_txn_to_Reducing_Output_Power_action() throws XtumlException {
    }

    private void Output_Power_Stable_and_ON_power_off_txn_to_Output_Power_Stable_and_OFF_action() throws XtumlException {
    }

    private void Output_Power_Stable_and_ON_increase_power_txn_to_Raising_Output_Power_action() throws XtumlException {
    }

    private void Output_Power_Stable_and_ON_decrease_power_txn_to_Reducing_Output_Power_action() throws XtumlException {
    }

    private void Raising_Output_Power_power_off_txn_to_Output_Power_Stable_and_OFF_action() throws XtumlException {
    }

    private void Raising_Output_Power_power_on_txn_to_Output_Power_Stable_and_ON_action() throws XtumlException {
    }

    private void Raising_Output_Power_increase_power_txn_to_Raising_Output_Power_action() throws XtumlException {
    }

    private void Raising_Output_Power_decrease_power_txn_to_Reducing_Output_Power_action() throws XtumlException {
    }

    private void Reducing_Output_Power_power_off_txn_to_Output_Power_Stable_and_OFF_action() throws XtumlException {
    }

    private void Reducing_Output_Power_power_on_txn_to_Output_Power_Stable_and_ON_action() throws XtumlException {
    }

    private void Reducing_Output_Power_increase_power_txn_to_Raising_Output_Power_action() throws XtumlException {
    }

    private void Reducing_Output_Power_decrease_power_txn_to_Reducing_Output_Power_action() throws XtumlException {
    }



    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
            { IGNORE,
              (event) -> {Output_Power_Stable_and_OFF_increase_power_txn_to_Raising_Output_Power_action();Raising_Output_Power_entry_action();return Raising_Output_Power;},
              (event) -> {Output_Power_Stable_and_OFF_decrease_power_txn_to_Reducing_Output_Power_action();Reducing_Output_Power_entry_action();return Reducing_Output_Power;},
              (event) -> {Output_Power_Stable_and_OFF_power_on_txn_to_Output_Power_Stable_and_ON_action();Output_Power_Stable_and_ON_entry_action();return Output_Power_Stable_and_ON;}
            },
            { (event) -> {Output_Power_Stable_and_ON_power_off_txn_to_Output_Power_Stable_and_OFF_action();Output_Power_Stable_and_OFF_entry_action();return Output_Power_Stable_and_OFF;},
              (event) -> {Output_Power_Stable_and_ON_increase_power_txn_to_Raising_Output_Power_action();Raising_Output_Power_entry_action();return Raising_Output_Power;},
              (event) -> {Output_Power_Stable_and_ON_decrease_power_txn_to_Reducing_Output_Power_action();Reducing_Output_Power_entry_action();return Reducing_Output_Power;},
              IGNORE
            },
            { (event) -> {Raising_Output_Power_power_off_txn_to_Output_Power_Stable_and_OFF_action();Output_Power_Stable_and_OFF_entry_action();return Output_Power_Stable_and_OFF;},
              (event) -> {Raising_Output_Power_increase_power_txn_to_Raising_Output_Power_action();Raising_Output_Power_entry_action();return Raising_Output_Power;},
              (event) -> {Raising_Output_Power_decrease_power_txn_to_Reducing_Output_Power_action();Reducing_Output_Power_entry_action();return Reducing_Output_Power;},
              (event) -> {Raising_Output_Power_power_on_txn_to_Output_Power_Stable_and_ON_action();Output_Power_Stable_and_ON_entry_action();return Output_Power_Stable_and_ON;}
            },
            { (event) -> {Reducing_Output_Power_power_off_txn_to_Output_Power_Stable_and_OFF_action();Output_Power_Stable_and_OFF_entry_action();return Output_Power_Stable_and_OFF;},
              (event) -> {Reducing_Output_Power_increase_power_txn_to_Raising_Output_Power_action();Raising_Output_Power_entry_action();return Raising_Output_Power;},
              (event) -> {Reducing_Output_Power_decrease_power_txn_to_Reducing_Output_Power_action();Reducing_Output_Power_entry_action();return Reducing_Output_Power;},
              (event) -> {Reducing_Output_Power_power_on_txn_to_Output_Power_Stable_and_ON_action();Output_Power_Stable_and_ON_entry_action();return Output_Power_Stable_and_ON;}
            }
        };
    }

    @Override
    public MagnetronTube self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "MagnetronTube";
    }

}
