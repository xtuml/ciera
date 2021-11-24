package components;


import components.microwaveoven.datatypes.Tube_Wattage;
import components.microwaveoven.externalentities.CP;
import components.microwaveoven.externalentities.impl.CPImpl;
import components.microwaveoven.microwaveoven.Beeper;
import components.microwaveoven.microwaveoven.BeeperSet;
import components.microwaveoven.microwaveoven.Door;
import components.microwaveoven.microwaveoven.DoorSet;
import components.microwaveoven.microwaveoven.InternalLight;
import components.microwaveoven.microwaveoven.InternalLightSet;
import components.microwaveoven.microwaveoven.MagnetronTube;
import components.microwaveoven.microwaveoven.MagnetronTubeSet;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.OvenSet;
import components.microwaveoven.microwaveoven.Turntable;
import components.microwaveoven.microwaveoven.TurntableSet;
import components.microwaveoven.microwaveoven.impl.BeeperImpl;
import components.microwaveoven.microwaveoven.impl.BeeperSetImpl;
import components.microwaveoven.microwaveoven.impl.DoorImpl;
import components.microwaveoven.microwaveoven.impl.DoorSetImpl;
import components.microwaveoven.microwaveoven.impl.InternalLightImpl;
import components.microwaveoven.microwaveoven.impl.InternalLightSetImpl;
import components.microwaveoven.microwaveoven.impl.MagnetronTubeImpl;
import components.microwaveoven.microwaveoven.impl.MagnetronTubeSetImpl;
import components.microwaveoven.microwaveoven.impl.OvenImpl;
import components.microwaveoven.microwaveoven.impl.OvenSetImpl;
import components.microwaveoven.microwaveoven.impl.TurntableImpl;
import components.microwaveoven.microwaveoven.impl.TurntableSetImpl;
import components.microwaveoven.testsubsystem.TestSequences;
import components.microwaveoven.testsubsystem.TestSequencesSet;
import components.microwaveoven.testsubsystem.impl.TestSequencesImpl;
import components.microwaveoven.testsubsystem.impl.TestSequencesSetImpl;

import io.ciera.runtime.instanceloading.sql.util.SQL;
import io.ciera.runtime.instanceloading.sql.util.impl.SQLImpl;
import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.classes.IRelationshipSet;
import io.ciera.runtime.summit.classes.Relationship;
import io.ciera.runtime.summit.classes.RelationshipSet;
import io.ciera.runtime.summit.components.Component;
import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.ModelIntegrityException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.StringUtil;
import io.ciera.runtime.summit.util.ARCH;
import io.ciera.runtime.summit.util.CMD;
import io.ciera.runtime.summit.util.TIM;
import io.ciera.runtime.summit.util.impl.ARCHImpl;
import io.ciera.runtime.summit.util.impl.CMDImpl;
import io.ciera.runtime.summit.util.impl.TIMImpl;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;


public class MicrowaveOven extends Component<MicrowaveOven> {

    private Map<String, Class<?>> classDirectory;

    public MicrowaveOven(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
        Beeper_extent = new BeeperSetImpl();
        Door_extent = new DoorSetImpl();
        InternalLight_extent = new InternalLightSetImpl();
        MagnetronTube_extent = new MagnetronTubeSetImpl();
        Oven_extent = new OvenSetImpl();
        TestSequences_extent = new TestSequencesSetImpl();
        Turntable_extent = new TurntableSetImpl();
        R1_Oven_houses_MagnetronTube_extent = new RelationshipSet();
        R2_Oven_is_illuminated_by_InternalLight_extent = new RelationshipSet();
        R3_Oven_features_Beeper_extent = new RelationshipSet();
        R4_Oven_is_accessed_via_Door_extent = new RelationshipSet();
        R5_Oven_has_Turntable_extent = new RelationshipSet();
        CMD = null;
        SQL = null;
        ARCH = null;
        TIM = null;
        CP = null;
        classDirectory = new TreeMap<>();
        classDirectory.put("MO_B", BeeperImpl.class);
        classDirectory.put("MO_D", DoorImpl.class);
        classDirectory.put("MO_IL", InternalLightImpl.class);
        classDirectory.put("MO_MT", MagnetronTubeImpl.class);
        classDirectory.put("MO_O", OvenImpl.class);
        classDirectory.put("MO_TS", TestSequencesImpl.class);
        classDirectory.put("MO_TRN", TurntableImpl.class);
    }

    // domain functions
    public void CancelCooking() throws XtumlException {
        Oven oven = context().Oven_instances().any();
        context().generate(new OvenImpl.cancel_cooking(getRunContext(), context().getId()).to(oven));
    }

    public void CloseDoor() throws XtumlException {
        Door door = context().Door_instances().any();
        context().generate(new DoorImpl.close(getRunContext(), context().getId()).to(door));
    }

    public void DecreasePower() throws XtumlException {
        MagnetronTube tube = context().MagnetronTube_instances().any();
        context().generate(new MagnetronTubeImpl.decrease_power(getRunContext(), context().getId()).to(tube));
    }

    public void DefineOven() throws XtumlException {
        Oven mo = OvenImpl.create( context() );
        mo.setRemaining_cooking_time(0);
        MagnetronTube tube = MagnetronTubeImpl.create( context() );
        context().relate_R1_Oven_houses_MagnetronTube( mo, tube );
        tube.setCurrent_power_output(Tube_Wattage.HIGH);
        InternalLight light = InternalLightImpl.create( context() );
        context().relate_R2_Oven_is_illuminated_by_InternalLight( mo, light );
        Beeper beeper = BeeperImpl.create( context() );
        context().relate_R3_Oven_features_Beeper( mo, beeper );
        Door door = DoorImpl.create( context() );
        context().relate_R4_Oven_is_accessed_via_Door( mo, door );
        door.setIs_secure(false);
        Turntable turntable = TurntableImpl.create( context() );
        context().relate_R5_Oven_has_Turntable( mo, turntable );
    }

    public void IncreasePower() throws XtumlException {
        MagnetronTube tube = context().MagnetronTube_instances().any();
        context().generate(new MagnetronTubeImpl.increase_power(getRunContext(), context().getId()).to(tube));
    }

    public void OpenDoor() throws XtumlException {
        Door door = context().Door_instances().any();
        context().generate(new DoorImpl.release(getRunContext(), context().getId()).to(door));
    }

    public void SpecifyCookingPeriod( final int p_cookingPeriod ) throws XtumlException {
        int timePeriod = 1000000 * p_cookingPeriod;
        Oven oven = context().Oven_instances().any();
        context().generate(new OvenImpl.cooking_period(getRunContext(), context().getId(),  timePeriod ).to(oven));
    }

    public void StartCooking() throws XtumlException {
        Oven oven = context().Oven_instances().any();
        context().generate(new OvenImpl.start_cooking(getRunContext(), context().getId()).to(oven));
    }

    public void TestSequence1() throws XtumlException {
        TestSequences testSequence = TestSequencesImpl.create( context() );
        context().generate(new TestSequencesImpl.perform_test_seq_1(getRunContext(), context().getId()).to(testSequence));
    }

    public void init() throws XtumlException {
        context().CMD().register_value( "cwd", "root_dir", "base working directory", "base working directory", false );
        context().CMD().register_value( "i", "input_file", "input file", "input file", false );
        context().CMD().read_command_line();
        String input_file = context().CMD().get_value( "i" );
        if ( StringUtil.inequality("", input_file) ) {
            context().SQL().load_file( ( context().CMD().get_value( "cwd" ) + "/" ) + input_file );
        }
        Oven oven = context().Oven_instances().any();
        if ( oven.isEmpty() ) {
            context().DefineOven();
            context().TestSequence1();
        }
    }



    // relates and unrelates
    public void relate_R1_Oven_houses_MagnetronTube( Oven form, MagnetronTube part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R1_Oven_houses_MagnetronTube_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR1_is_housed_in_Oven(form);
            form.setR1_houses_MagnetronTube(part);
            form.setTubeID( part.getTubeID() );
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R1_Oven_houses_MagnetronTube( Oven form, MagnetronTube part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R1_Oven_houses_MagnetronTube_extent.remove( R1_Oven_houses_MagnetronTube_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR1_is_housed_in_Oven(OvenImpl.EMPTY_OVEN);
            form.setR1_houses_MagnetronTube(MagnetronTubeImpl.EMPTY_MAGNETRONTUBE);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
    public void relate_R2_Oven_is_illuminated_by_InternalLight( Oven form, InternalLight part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R2_Oven_is_illuminated_by_InternalLight_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR2_illuminates_Oven(form);
            form.setR2_is_illuminated_by_InternalLight(part);
            form.setLightID( part.getLightID() );
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R2_Oven_is_illuminated_by_InternalLight( Oven form, InternalLight part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R2_Oven_is_illuminated_by_InternalLight_extent.remove( R2_Oven_is_illuminated_by_InternalLight_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR2_illuminates_Oven(OvenImpl.EMPTY_OVEN);
            form.setR2_is_illuminated_by_InternalLight(InternalLightImpl.EMPTY_INTERNALLIGHT);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
    public void relate_R3_Oven_features_Beeper( Oven form, Beeper part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R3_Oven_features_Beeper_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR3_is_located_in_Oven(form);
            form.setR3_features_Beeper(part);
            form.setBeeperID( part.getBeeperID() );
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R3_Oven_features_Beeper( Oven form, Beeper part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R3_Oven_features_Beeper_extent.remove( R3_Oven_features_Beeper_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR3_is_located_in_Oven(OvenImpl.EMPTY_OVEN);
            form.setR3_features_Beeper(BeeperImpl.EMPTY_BEEPER);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
    public void relate_R4_Oven_is_accessed_via_Door( Oven form, Door part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R4_Oven_is_accessed_via_Door_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR4_provides_access_to_Oven(form);
            form.setR4_is_accessed_via_Door(part);
            form.setDoorID( part.getDoorID() );
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R4_Oven_is_accessed_via_Door( Oven form, Door part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R4_Oven_is_accessed_via_Door_extent.remove( R4_Oven_is_accessed_via_Door_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR4_provides_access_to_Oven(OvenImpl.EMPTY_OVEN);
            form.setR4_is_accessed_via_Door(DoorImpl.EMPTY_DOOR);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
    public void relate_R5_Oven_has_Turntable( Oven form, Turntable part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO cardinality check
        if ( R5_Oven_has_Turntable_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR5_occupies_Oven(form);
            form.setR5_has_Turntable(part);
            form.setTurntableID( part.getTurntableID() );
        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_R5_Oven_has_Turntable( Oven form, Turntable part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( R5_Oven_has_Turntable_extent.remove( R5_Oven_has_Turntable_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
            part.setR5_occupies_Oven(OvenImpl.EMPTY_OVEN);
            form.setR5_has_Turntable(TurntableImpl.EMPTY_TURNTABLE);
        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }


    // instance selections
    private BeeperSet Beeper_extent;
    public BeeperSet Beeper_instances() {
        return Beeper_extent;
    }
    private DoorSet Door_extent;
    public DoorSet Door_instances() {
        return Door_extent;
    }
    private InternalLightSet InternalLight_extent;
    public InternalLightSet InternalLight_instances() {
        return InternalLight_extent;
    }
    private MagnetronTubeSet MagnetronTube_extent;
    public MagnetronTubeSet MagnetronTube_instances() {
        return MagnetronTube_extent;
    }
    private OvenSet Oven_extent;
    public OvenSet Oven_instances() {
        return Oven_extent;
    }
    private TestSequencesSet TestSequences_extent;
    public TestSequencesSet TestSequences_instances() {
        return TestSequences_extent;
    }
    private TurntableSet Turntable_extent;
    public TurntableSet Turntable_instances() {
        return Turntable_extent;
    }


    // relationship selections
    private IRelationshipSet R1_Oven_houses_MagnetronTube_extent;
    public IRelationshipSet R1_Oven_houses_MagnetronTubes() throws XtumlException {
        return R1_Oven_houses_MagnetronTube_extent;
    }
    private IRelationshipSet R2_Oven_is_illuminated_by_InternalLight_extent;
    public IRelationshipSet R2_Oven_is_illuminated_by_InternalLights() throws XtumlException {
        return R2_Oven_is_illuminated_by_InternalLight_extent;
    }
    private IRelationshipSet R3_Oven_features_Beeper_extent;
    public IRelationshipSet R3_Oven_features_Beepers() throws XtumlException {
        return R3_Oven_features_Beeper_extent;
    }
    private IRelationshipSet R4_Oven_is_accessed_via_Door_extent;
    public IRelationshipSet R4_Oven_is_accessed_via_Doors() throws XtumlException {
        return R4_Oven_is_accessed_via_Door_extent;
    }
    private IRelationshipSet R5_Oven_has_Turntable_extent;
    public IRelationshipSet R5_Oven_has_Turntables() throws XtumlException {
        return R5_Oven_has_Turntable_extent;
    }


    // ports


    // utilities
    private CMD CMD;
    public CMD CMD() {
        if ( null == CMD ) CMD = new CMDImpl<>( this );
        return CMD;
    }
    private SQL SQL;
    public SQL SQL() {
        if ( null == SQL ) SQL = new SQLImpl<>( this );
        return SQL;
    }
    private ARCH ARCH;
    public ARCH ARCH() {
        if ( null == ARCH ) ARCH = new ARCHImpl<>( this );
        return ARCH;
    }
    private TIM TIM;
    public TIM TIM() {
        if ( null == TIM ) TIM = new TIMImpl<>( this );
        return TIM;
    }
    private CP CP;
    public CP CP() {
        if ( null == CP ) CP = new CPImpl<>( this );
        return CP;
    }


    // component initialization function
    @Override
    public void initialize() throws XtumlException {
        init();
    }

    @Override
    public String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("MicrowaveOvenProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("MicrowaveOvenProperties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version_date", "Unknown");
    }

    @Override
    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot add empty instance to population." );
        if ( instance instanceof Beeper ) return Beeper_extent.add( (Beeper)instance );
        else if ( instance instanceof Door ) return Door_extent.add( (Door)instance );
        else if ( instance instanceof InternalLight ) return InternalLight_extent.add( (InternalLight)instance );
        else if ( instance instanceof MagnetronTube ) return MagnetronTube_extent.add( (MagnetronTube)instance );
        else if ( instance instanceof Oven ) return Oven_extent.add( (Oven)instance );
        else if ( instance instanceof TestSequences ) return TestSequences_extent.add( (TestSequences)instance );
        else if ( instance instanceof Turntable ) return Turntable_extent.add( (Turntable)instance );
        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
        if ( instance instanceof Beeper ) return Beeper_extent.remove( (Beeper)instance );
        else if ( instance instanceof Door ) return Door_extent.remove( (Door)instance );
        else if ( instance instanceof InternalLight ) return InternalLight_extent.remove( (InternalLight)instance );
        else if ( instance instanceof MagnetronTube ) return MagnetronTube_extent.remove( (MagnetronTube)instance );
        else if ( instance instanceof Oven ) return Oven_extent.remove( (Oven)instance );
        else if ( instance instanceof TestSequences ) return TestSequences_extent.remove( (TestSequences)instance );
        else if ( instance instanceof Turntable ) return Turntable_extent.remove( (Turntable)instance );
        return false;
    }

    @Override
    public MicrowaveOven context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
