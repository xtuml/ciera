package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Beeper;
import components.microwaveoven.microwaveoven.Door;
import components.microwaveoven.microwaveoven.InternalLight;
import components.microwaveoven.microwaveoven.MagnetronTube;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.Turntable;
import components.microwaveoven.microwaveoven.impl.BeeperImpl;
import components.microwaveoven.microwaveoven.impl.InternalLightImpl;
import components.microwaveoven.microwaveoven.impl.MagnetronTubeImpl;
import components.microwaveoven.microwaveoven.impl.OvenImpl;
import components.microwaveoven.microwaveoven.impl.TurntableImpl;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.statemachine.ITransition;
import io.ciera.runtime.summit.statemachine.StateMachine;


public class OvenStateMachine extends StateMachine<Oven,MicrowaveOven> {

    public static final int Assigning_Cooking_Period = 1;
    public static final int Awaiting_Cooking_Request = 0;
    public static final int Cooking = 2;
    public static final int Cooking_Suspended = 3;
    public static final int Ensuring_Safe_to_Cook = 4;
    public static final int Signalling_Cooking_Period_Over = 5;


    private Oven self;

    public OvenStateMachine(Oven self, MicrowaveOven context) {
        this(self, context, Awaiting_Cooking_Request);
    }

    public OvenStateMachine(Oven self, MicrowaveOven context, int initialState) {
        super(context, initialState);
        this.self = self;
    }

    private void Assigning_Cooking_Period_entry_action( final int p_period ) throws XtumlException {
        self().setRemaining_cooking_time(p_period);
    }

    private void Awaiting_Cooking_Request_entry_action() throws XtumlException {
        self().setRemaining_cooking_time(0);
        Beeper beeper = self().R3_features_Beeper();
        context().generate(new BeeperImpl.stop_beeping(getRunContext(), context().getId()).to(beeper));
    }

    private void Cooking_entry_action() throws XtumlException {
        EventHandle cooking_over = new OvenImpl.cooking_period_over(getRunContext(), context().getId()).toSelf(self());
        self().setOven_timer(context().TIM().timer_start( cooking_over, self().getRemaining_cooking_time() ));
        InternalLight light = self().R2_is_illuminated_by_InternalLight();
        context().generate(new InternalLightImpl.switch_on(getRunContext(), context().getId()).to(light));
        Turntable turntable = self().R5_has_Turntable();
        context().generate(new TurntableImpl.spin(getRunContext(), context().getId()).to(turntable));
        MagnetronTube tube = self().R1_houses_MagnetronTube();
        context().generate(new MagnetronTubeImpl.power_on(getRunContext(), context().getId()).to(tube));
    }

    private void Cooking_Suspended_entry_action() throws XtumlException {
        self().setRemaining_cooking_time(context().TIM().timer_remaining_time( self().getOven_timer() ));
        context().TIM().timer_cancel( self().getOven_timer() );
        InternalLight light = self().R2_is_illuminated_by_InternalLight();
        context().generate(new InternalLightImpl.switch_off(getRunContext(), context().getId()).to(light));
        Turntable turntable = self().R5_has_Turntable();
        context().generate(new TurntableImpl.stop(getRunContext(), context().getId()).to(turntable));
        MagnetronTube tube = self().R1_houses_MagnetronTube();
        context().generate(new MagnetronTubeImpl.power_off(getRunContext(), context().getId()).to(tube));
    }

    private void Ensuring_Safe_to_Cook_entry_action() throws XtumlException {
        if ( self().getRemaining_cooking_time() != 0 ) {
            Door door = self().R4_is_accessed_via_Door();
            if ( door.getIs_secure() == true ) {
                context().generate(new OvenImpl.oven_safe(getRunContext(), context().getId()).toSelf(self()));
            }
        }
    }

    private void Signalling_Cooking_Period_Over_entry_action() throws XtumlException {
        Beeper beeper = self().R3_features_Beeper();
        context().generate(new BeeperImpl.start_beeping(getRunContext(), context().getId()).to(beeper));
        InternalLight light = self().R2_is_illuminated_by_InternalLight();
        context().generate(new InternalLightImpl.switch_off(getRunContext(), context().getId()).to(light));
        Turntable turntable = self().R5_has_Turntable();
        context().generate(new TurntableImpl.stop(getRunContext(), context().getId()).to(turntable));
        MagnetronTube tube = self().R1_houses_MagnetronTube();
        context().generate(new MagnetronTubeImpl.power_off(getRunContext(), context().getId()).to(tube));
    }



    private void Assigning_Cooking_Period_start_cooking_txn_to_Ensuring_Safe_to_Cook_action() throws XtumlException {
    }

    private void Awaiting_Cooking_Request_cooking_period_txn_to_Assigning_Cooking_Period_action( final int p_period ) throws XtumlException {
    }

    private void Cooking_Suspended_cancel_cooking_txn_to_Awaiting_Cooking_Request_action() throws XtumlException {
    }

    private void Cooking_Suspended_start_cooking_txn_to_Ensuring_Safe_to_Cook_action() throws XtumlException {
    }

    private void Cooking_cancel_cooking_txn_to_Cooking_Suspended_action() throws XtumlException {
    }

    private void Cooking_cooking_period_over_txn_to_Signalling_Cooking_Period_Over_action() throws XtumlException {
    }

    private void Ensuring_Safe_to_Cook_cancel_cooking_txn_to_Awaiting_Cooking_Request_action() throws XtumlException {
    }

    private void Ensuring_Safe_to_Cook_oven_safe_txn_to_Cooking_action() throws XtumlException {
    }

    private void Ensuring_Safe_to_Cook_start_cooking_txn_to_Ensuring_Safe_to_Cook_action() throws XtumlException {
    }

    private void Signalling_Cooking_Period_Over_cancel_cooking_txn_to_Awaiting_Cooking_Request_action() throws XtumlException {
    }

    private void Signalling_Cooking_Period_Over_beeping_over_txn_to_Awaiting_Cooking_Request_action() throws XtumlException {
    }



    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
            { IGNORE,
              (event) -> {Awaiting_Cooking_Request_cooking_period_txn_to_Assigning_Cooking_Period_action((int)event.get(0));Assigning_Cooking_Period_entry_action((int)event.get(0));return Assigning_Cooking_Period;},
              IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              IGNORE
            },
            { IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              (event) -> {Assigning_Cooking_Period_start_cooking_txn_to_Ensuring_Safe_to_Cook_action();Ensuring_Safe_to_Cook_entry_action();return Ensuring_Safe_to_Cook;}
            },
            { IGNORE,
              IGNORE,
              (event) -> {Cooking_cooking_period_over_txn_to_Signalling_Cooking_Period_Over_action();Signalling_Cooking_Period_Over_entry_action();return Signalling_Cooking_Period_Over;},
              IGNORE,
              IGNORE,
              (event) -> {Cooking_cancel_cooking_txn_to_Cooking_Suspended_action();Cooking_Suspended_entry_action();return Cooking_Suspended;},
              IGNORE,
              IGNORE
            },
            { IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              (event) -> {Cooking_Suspended_cancel_cooking_txn_to_Awaiting_Cooking_Request_action();Awaiting_Cooking_Request_entry_action();return Awaiting_Cooking_Request;},
              IGNORE,
              (event) -> {Cooking_Suspended_start_cooking_txn_to_Ensuring_Safe_to_Cook_action();Ensuring_Safe_to_Cook_entry_action();return Ensuring_Safe_to_Cook;}
            },
            { (event) -> {Ensuring_Safe_to_Cook_oven_safe_txn_to_Cooking_action();Cooking_entry_action();return Cooking;},
              IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              (event) -> {Ensuring_Safe_to_Cook_cancel_cooking_txn_to_Awaiting_Cooking_Request_action();Awaiting_Cooking_Request_entry_action();return Awaiting_Cooking_Request;},
              IGNORE,
              (event) -> {Ensuring_Safe_to_Cook_start_cooking_txn_to_Ensuring_Safe_to_Cook_action();Ensuring_Safe_to_Cook_entry_action();return Ensuring_Safe_to_Cook;}
            },
            { IGNORE,
              IGNORE,
              IGNORE,
              IGNORE,
              (event) -> {Signalling_Cooking_Period_Over_beeping_over_txn_to_Awaiting_Cooking_Request_action();Awaiting_Cooking_Request_entry_action();return Awaiting_Cooking_Request;},
              (event) -> {Signalling_Cooking_Period_Over_cancel_cooking_txn_to_Awaiting_Cooking_Request_action();Awaiting_Cooking_Request_entry_action();return Awaiting_Cooking_Request;},
              IGNORE,
              IGNORE
            }
        };
    }

    @Override
    public Oven self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "Oven";
    }

}
