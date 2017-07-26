package microwaveovenciera.components.microwaveoven;

import ciera.application.ApplicationThread;
import ciera.classes.Where;
import ciera.components.Component;
import ciera.exceptions.XtumlException;
import microwaveovenciera.components.microwaveoven.functions.Functions;
import microwaveovenciera.components.microwaveoven.microwaveoven.*;
import microwaveovenciera.components.microwaveoven.testsubsystem.TestSequences;

public class MicrowaveOven extends Component {
    
    private static final Class<?>[] classes = new Class<?>[] {
        Beeper.class, Door.class, InternalLight.class, MagnetronTube.class, Oven.class, Turntable.class, TestSequences.class
    };
    
    public MicrowaveOven( ApplicationThread defaultThread ) {
        super( defaultThread );
    }
    
    // selections
    public Beeper selectAnyMO_BFromInstances() {
        return ((BeeperSet)getInstanceSet(Beeper.class)).selectAnyMO_BFromInstances( null );
    }
    
    public Beeper selectAnyMO_BFromInstances( Where condition ) {
        return ((BeeperSet)getInstanceSet(Beeper.class)).selectAnyMO_BFromInstances( condition );
    }

    public BeeperSet selectManyMO_BsFromInstances() {
        return (BeeperSet)getInstanceSet(Beeper.class);
    }

    public BeeperSet selectManyMO_BsFromInstances( Where condition ) {
        return ((BeeperSet)getInstanceSet(Beeper.class)).selectManyMO_BsFromInstances( condition );
    }

    public Door selectAnyMO_DFromInstances() {
        return ((DoorSet)getInstanceSet(Door.class)).selectAnyMO_DFromInstances( null );
    }
    
    public Door selectAnyMO_DFromInstances( Where condition ) {
        return ((DoorSet)getInstanceSet(Door.class)).selectAnyMO_DFromInstances( condition );
    }

    public DoorSet selectManyMO_DsFromInstances() {
        return (DoorSet)getInstanceSet(Door.class);
    }

    public DoorSet selectManyMO_DsFromInstances( Where condition ) {
        return ((DoorSet)getInstanceSet(Door.class)).selectManyMO_DsFromInstances( condition );
    }

    public InternalLight selectAnyMO_ILFromInstances() {
        return ((InternalLightSet)getInstanceSet(InternalLight.class)).selectAnyMO_ILFromInstances( null );
    }
    
    public InternalLight selectAnyMO_ILFromInstances( Where condition ) {
        return ((InternalLightSet)getInstanceSet(InternalLight.class)).selectAnyMO_ILFromInstances( condition );
    }

    public InternalLightSet selectManyMO_ILsFromInstances() {
        return (InternalLightSet)getInstanceSet(InternalLight.class);
    }

    public InternalLightSet selectManyMO_ILsFromInstances( Where condition ) {
        return ((InternalLightSet)getInstanceSet(InternalLight.class)).selectManyMO_ILsFromInstances( condition );
    }

    public MagnetronTube selectAnyMO_MTFromInstances() {
        return ((MagnetronTubeSet)getInstanceSet(MagnetronTube.class)).selectAnyMO_MTFromInstances( null );
    }
    
    public MagnetronTube selectAnyMO_MTFromInstances( Where condition ) {
        return ((MagnetronTubeSet)getInstanceSet(MagnetronTube.class)).selectAnyMO_MTFromInstances( condition );
    }

    public MagnetronTubeSet selectManyMO_MTsFromInstances() {
        return (MagnetronTubeSet)getInstanceSet(MagnetronTube.class);
    }

    public MagnetronTubeSet selectManyMO_MTsFromInstances( Where condition ) {
        return ((MagnetronTubeSet)getInstanceSet(MagnetronTube.class)).selectManyMO_MTsFromInstances( condition );
    }

    public Oven selectAnyMO_OFromInstances() {
        return ((OvenSet)getInstanceSet(Oven.class)).selectAnyMO_OFromInstances( null );
    }
    
    public Oven selectAnyMO_OFromInstances( Where condition ) {
        return ((OvenSet)getInstanceSet(Oven.class)).selectAnyMO_OFromInstances( condition );
    }

    public OvenSet selectManyMO_OsFromInstances() {
        return (OvenSet)getInstanceSet(Oven.class);
    }

    public OvenSet selectManyMO_OsFromInstances( Where condition ) {
        return ((OvenSet)getInstanceSet(Oven.class)).selectManyMO_OsFromInstances( condition );
    }

    public Turntable selectAnyMO_TRNFromInstances() {
        return ((TurntableSet)getInstanceSet(Turntable.class)).selectAnyMO_TRNFromInstances( null );
    }
    
    public Turntable selectAnyMO_TRNFromInstances( Where condition ) {
        return ((TurntableSet)getInstanceSet(Turntable.class)).selectAnyMO_TRNFromInstances( condition );
    }

    public TurntableSet selectManyMO_TRNsFromInstances() {
        return (TurntableSet)getInstanceSet(Turntable.class);
    }

    public TurntableSet selectManyMO_TRNsFromInstances( Where condition ) {
        return ((TurntableSet)getInstanceSet(Turntable.class)).selectManyMO_TRNsFromInstances( condition );
    }

    // component initialization function
    @Override
    public void initialize() throws XtumlException {
        Functions.DefineOven( this );
        Functions.TestSequence1( this );
    }

    @Override
    public Class<?>[] getClasses() {
        return classes;
    }

}
