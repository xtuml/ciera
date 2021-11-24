package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Beeper;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.impl.BeeperImpl;
import components.microwaveoven.microwaveoven.impl.OvenImpl;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.statemachine.ITransition;
import io.ciera.runtime.summit.statemachine.StateMachine;


public class BeeperStateMachine extends StateMachine<Beeper,MicrowaveOven> {

    public static final int Awaiting_Beeper_Request = 0;
    public static final int Beeping = 1;


    private Beeper self;

    public BeeperStateMachine(Beeper self, MicrowaveOven context) {
        this(self, context, Awaiting_Beeper_Request);
    }

    public BeeperStateMachine(Beeper self, MicrowaveOven context, int initialState) {
        super(context, initialState);
        this.self = self;
    }

    private void Awaiting_Beeper_Request_entry_action() throws XtumlException {
        self().setBeep_count(0);
        context().TIM().timer_cancel( self().getBeeper_timer() );
    }

    private void Beeping_entry_action() throws XtumlException {
        if ( self().getBeep_count() == 0 ) {
            EventHandle delay_over = new BeeperImpl.beep_delay_over(getRunContext(), context().getId()).toSelf(self());
            self().setBeeper_timer(context().TIM().timer_start( delay_over, 100000 ));
        }
        else if ( self().getBeep_count() == 4 ) {
            context().generate(new BeeperImpl.beeping_stopped(getRunContext(), context().getId()).toSelf(self()));
            Oven oven = self().R3_is_located_in_Oven();
            context().generate(new OvenImpl.beeping_over(getRunContext(), context().getId()).to(oven));
        }
        else {
            EventHandle delay_over = new BeeperImpl.beep_delay_over(getRunContext(), context().getId()).toSelf(self());
            self().setBeeper_timer(context().TIM().timer_start( delay_over, 100000 ));
        }
        self().setBeep_count(self().getBeep_count() + 1);
    }



    private void Awaiting_Beeper_Request_start_beeping_txn_to_Beeping_action() throws XtumlException {
    }

    private void Beeping_stop_beeping_txn_to_Awaiting_Beeper_Request_action() throws XtumlException {
    }

    private void Beeping_beeping_stopped_txn_to_Awaiting_Beeper_Request_action() throws XtumlException {
    }

    private void Beeping_beep_delay_over_txn_to_Beeping_action() throws XtumlException {
    }



    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
            { (event) -> {Awaiting_Beeper_Request_start_beeping_txn_to_Beeping_action();Beeping_entry_action();return Beeping;},
              IGNORE,
              IGNORE,
              IGNORE
            },
            { IGNORE,
              (event) -> {Beeping_beep_delay_over_txn_to_Beeping_action();Beeping_entry_action();return Beeping;},
              (event) -> {Beeping_stop_beeping_txn_to_Awaiting_Beeper_Request_action();Awaiting_Beeper_Request_entry_action();return Awaiting_Beeper_Request;},
              (event) -> {Beeping_beeping_stopped_txn_to_Awaiting_Beeper_Request_action();Awaiting_Beeper_Request_entry_action();return Awaiting_Beeper_Request;}
            }
        };
    }

    @Override
    public Beeper self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "Beeper";
    }

}
