package microwaveovenciera.components.microwaveoven.functions;

import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.InstancePopulationException;
import ciera.classes.exceptions.LinkException;
import microwaveovenciera.components.microwaveoven.MicrowaveOven;
import microwaveovenciera.components.microwaveoven.datatypes.TubeWattage;
import microwaveovenciera.components.microwaveoven.microwaveoven.*;
import microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine.Close;
import microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine.Release;
import microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine.CancelCooking;
import microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine.CookingPeriod;
import microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine.StartCooking;
import microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine.DecreasePower;
import microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine.IncreasePower;

public class Functions {
    
    public static void CancelCooking( MicrowaveOven context ) throws EmptyInstanceException {
        // select any oven from instances of MO_O;
        Oven oven = context.selectAnyMO_OFromInstances();
        // generate MO_O4:'cancel_cooking'  to oven;
        oven.generateTo( new CancelCooking() );
    }

    public static void CloseDoor( MicrowaveOven context ) throws EmptyInstanceException {
        // select any door from instances of MO_D;
        Door door = context.selectAnyMO_DFromInstances();
        // generate MO_D2:'close'  to door;
        door.generateTo( new Close() );
    }
    
    public static void DecreasePower( MicrowaveOven context ) throws EmptyInstanceException {
        // select any tube from instances of MO_MT;
        MagnetronTube tube = context.selectAnyMO_MTFromInstances();
        // generate MO_MT2:'decrease_power' to tube;
        tube.generateTo( new DecreasePower() );
    }
    
    public static void DefineOven( MicrowaveOven context ) throws InstancePopulationException, EmptyInstanceException, LinkException {
        // Create the instances in the system.
        // create object instance mo of MO_O;
        Oven mo = context.createObjectInstance( new Oven() );
        // assign mo.remaining_cooking_time = 0;
        mo.setM_remaining_cooking_time( 0 );
        // create object instance tube of MO_MT;
        MagnetronTube tube = context.createObjectInstance( new MagnetronTube() );
        // relate mo to tube across R1;
        mo.relateToMO_MTAcrossR1( tube );
        // assign tube.current_power_output = tube_wattage::high;
        tube.setM_current_power_output( TubeWattage.HIGH );
        // create object instance light of MO_IL;
        InternalLight light = context.createObjectInstance( new InternalLight() );
        // relate mo to light across R2;
        mo.relateToMO_ILAcrossR2( light );
        // create object instance beeper of MO_B;
        Beeper beeper = context.createObjectInstance( new Beeper() );
        // relate mo to beeper across R3;
        mo.relateToMO_BAcrossR3( beeper );
        // create object instance door of MO_D;
        Door door = context.createObjectInstance( new Door() );
        // relate mo to door across R4;
        mo.relateToMO_DAcrossR4( door );
        // assign door.is_secure = false;
        door.setM_is_secure( false );
        // create object instance turntable of MO_TRN;
        Turntable turntable = context.createObjectInstance( new Turntable() );
        // relate mo to turntable across R5;
        mo.relateToMO_TRNAcrossR5( turntable );
    }
    
    public static void IncreasePower( MicrowaveOven context ) throws EmptyInstanceException {
        // select any tube from instances of MO_MT;
        MagnetronTube tube = context.selectAnyMO_MTFromInstances();
        // generate MO_MT1:'increase_power'  to tube;
        tube.generateTo( new IncreasePower() );
    }
    
    public static void OpenDoor( MicrowaveOven context ) throws EmptyInstanceException {
        // select any door from instances of MO_D;
        Door door = context.selectAnyMO_DFromInstances();
        // generate MO_D1:'release'  to door;
        door.generateTo( new Release() );
    }
    
    public static void SpecifyCookingPeriod( MicrowaveOven context, int cookingPeriod ) throws EmptyInstanceException {
        // cooking period is to be specified in seconds and must be converted to usec in order
        // to be compatible with BP's view of time
        // timePeriod = 1000000 * param.cookingPeriod;
        int timePeriod = 1000000 * cookingPeriod;
        // select any oven from instances of MO_O;
        Oven oven = context.selectAnyMO_OFromInstances();
        // generate MO_O8:'cooking_period' (period:timePeriod) to oven;
        oven.generateTo( new CookingPeriod( timePeriod ) );
    }
    
    public static void StartCooking( MicrowaveOven context ) throws EmptyInstanceException {
        // select any oven from instances of MO_O;
        Oven oven = context.selectAnyMO_OFromInstances();
        // generate MO_O3:'start_cooking'  to oven;
        oven.generateTo( new StartCooking() );
    }
    
    public static void TestSequence1( MicrowaveOven context ) {
        // create object instance testSequence of MO_TS;
        // generate MO_TS2 to testSequence;
    }

}
