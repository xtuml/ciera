package microwaveovenciera.components.microwaveoven.functions;

import ciera.classes.InstancePopulation;

public class Functions {
    
    public static void CancelCooking( InstancePopulation context ) {
        // select any oven from instances of MO_O;
        //Door oven = context.getInstanceSet( Oven.class ).selectAny();
        // generate MO_O4:'cancel_cooking'  to oven;
    }

    public static void CloseDoor( InstancePopulation context ) {
        // select any door from instances of MO_D;
        // generate MO_D2:'close'  to door;
    }
    
    public static void DecreasePower( InstancePopulation context ) {
        // select any tube from instances of MO_MT;
        // generate MO_MT2:'decrease_power' to tube;
    }
    
    public static void DefineOven( InstancePopulation context ) {
        // Create the instances in the system.
        // create object instance mo of MO_O;
        // assign mo.remaining_cooking_time = 0;
        // create object instance tube of MO_MT;
        // relate mo to tube across R1;
        // assign tube.current_power_output = tube_wattage::high;
        // create object instance light of MO_IL;
        // relate mo to light across R2;
        // create object instance beeper of MO_B;
        // relate mo to beeper across R3;
        // create object instance door of MO_D;
        // relate mo to door across R4;
        // assign door.is_secure = false;
        // create object instance turntable of MO_TRN;
        // relate mo to turntable across R5;
    }
    
    public static void IncreasePower( InstancePopulation context ) {
        // select any tube from instances of MO_MT;
        // generate MO_MT1:'increase_power'  to tube;
    }
    
    public static void OpenDoor( InstancePopulation context ) {
        // select any door from instances of MO_D;
        // generate MO_D1:'release'  to door;
    }
    
    public static void SpecifyCookingPeriod( InstancePopulation context, int cookingPeriod ) {
        // cooking period is to be specified in seconds and must be converted to usec in order
        // to be compatible with BP's view of time
        // 
        // timePeriod = 1000000 * param.cookingPeriod;
        // select any oven from instances of MO_O;
        // generate MO_O8:'cooking_period' (period:timePeriod) to oven;
    }
    
    public static void StartCooking( InstancePopulation context ) {
        // select any oven from instances of MO_O;
        // generate MO_O3:'start_cooking'  to oven;
    }
    
    public static void TestSequence1( InstancePopulation context ) {
        // create object instance testSequence of MO_TS;
        // generate MO_TS2 to testSequence;
    }

}
