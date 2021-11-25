package components;

import components.microwaveoven.datatypes.Tube_Wattage;
import components.microwaveoven.externalentities.CP;
import components.microwaveoven.microwaveoven.Beeper;
import components.microwaveoven.microwaveoven.Door;
import components.microwaveoven.microwaveoven.DoorStateMachine;
import components.microwaveoven.microwaveoven.InternalLight;
import components.microwaveoven.microwaveoven.MagnetronTube;
import components.microwaveoven.microwaveoven.MagnetronTubeStateMachine;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.OvenStateMachine;
import components.microwaveoven.microwaveoven.Turntable;
import components.microwaveoven.testsubsystem.TestSequences;
import components.microwaveoven.testsubsystem.TestSequencesStateMachine;
import io.ciera.runtime.application.Application;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.exceptions.EmptyInstanceException;
import io.ciera.runtime.util.ARCH;
import io.ciera.runtime.util.CMD;
import io.ciera.runtime.util.TIM;

public class MicrowaveOven extends Domain {

    public MicrowaveOven(String name, Application application) {
        super(name, application);
        this.TerminatorARCH = null;
        this.TerminatorCMD = null;
        this.TerminatorCP = null;
        this.TerminatorTIM = null;
    }

    // domain functions
    public void CancelCooking() {
        Oven oven = getDomain().Oven_instances().any();
        oven.getContext().generateEvent(OvenStateMachine.cancel_cooking.class, oven);
    }

    public void CloseDoor() {
        Door door = getDomain().Door_instances().any();
        door.getContext().generateEvent(DoorStateMachine.close.class, door);
    }

    public void DecreasePower() {
        MagnetronTube tube = getDomain().MagnetronTube_instances().any();
        tube.getContext().generateEvent(MagnetronTubeStateMachine.decrease_power.class, tube);
    }

    public void DefineOven() {
        Oven mo = getDomain().createInstance(Oven.class);
        mo.setRemaining_cooking_time(0);
        MagnetronTube tube = getDomain().createInstance(MagnetronTube.class);
        getDomain().relate_R1_Oven_houses_MagnetronTube(mo, tube);
        tube.setCurrent_power_output(Tube_Wattage.HIGH);
        InternalLight light = getDomain().createInstance(InternalLight.class);
        getDomain().relate_R2_Oven_is_illuminated_by_InternalLight(mo, light);
        Beeper beeper = getDomain().createInstance(Beeper.class);
        getDomain().relate_R3_Oven_features_Beeper(mo, beeper);
        Door door = getDomain().createInstance(Door.class);
        getDomain().relate_R4_Oven_is_accessed_via_Door(mo, door);
        door.setIs_secure(false);
        Turntable turntable = getDomain().createInstance(Turntable.class);
        getDomain().relate_R5_Oven_has_Turntable(mo, turntable);
    }

    public void IncreasePower() {
        MagnetronTube tube = getDomain().MagnetronTube_instances().any();
        tube.getContext().generateEvent(MagnetronTubeStateMachine.increase_power.class, tube);
    }

    public void OpenDoor() {
        Door door = getDomain().Door_instances().any();
        door.getContext().generateEvent(DoorStateMachine.release.class, door);
    }

    public void SpecifyCookingPeriod(final int p_cookingPeriod) {
        int timePeriod = 1000000 * p_cookingPeriod;
        Oven oven = getDomain().Oven_instances().any();
        oven.getContext().generateEvent(OvenStateMachine.cooking_period.class, oven, timePeriod);
    }

    public void StartCooking() {
        Oven oven = getDomain().Oven_instances().any();
        oven.getContext().generateEvent(OvenStateMachine.start_cooking.class, oven);
    }

    public void TestSequence1() {
        TestSequences testSequence = getDomain().createInstance(TestSequences.class);
        testSequence.getContext().generateEvent(TestSequencesStateMachine.perform_test_seq_1.class, testSequence);
    }

    public void init() {
        Oven oven = getDomain().Oven_instances().any();
        if (oven.isEmpty()) {
            getDomain().DefineOven();
            getDomain().TestSequence1();
        } else {
            getDomain().ARCH().shutdown();
        }
    }

    // relates and unrelates
    public void relate_R1_Oven_houses_MagnetronTube(Oven form, MagnetronTube part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot relate empty instances.");
        part.setR1_is_housed_in_Oven(form);
        form.setR1_houses_MagnetronTube(part);
        form.setTubeID(part.getTubeID());
    }

    public void unrelate_R1_Oven_houses_MagnetronTube(Oven form, MagnetronTube part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot unrelate empty instances.");
        part.setR1_is_housed_in_Oven(Oven.EMPTY);
        form.setR1_houses_MagnetronTube(MagnetronTube.EMPTY);
    }

    public void relate_R2_Oven_is_illuminated_by_InternalLight(Oven form, InternalLight part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot relate empty instances.");
        part.setR2_illuminates_Oven(form);
        form.setR2_is_illuminated_by_InternalLight(part);
        form.setLightID(part.getLightID());
    }

    public void unrelate_R2_Oven_is_illuminated_by_InternalLight(Oven form, InternalLight part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot unrelate empty instances.");
        part.setR2_illuminates_Oven(Oven.EMPTY);
        form.setR2_is_illuminated_by_InternalLight(InternalLight.EMPTY);
    }

    public void relate_R3_Oven_features_Beeper(Oven form, Beeper part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot relate empty instances.");
        part.setR3_is_located_in_Oven(form);
        form.setR3_features_Beeper(part);
        form.setBeeperID(part.getBeeperID());
    }

    public void unrelate_R3_Oven_features_Beeper(Oven form, Beeper part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot unrelate empty instances.");
        part.setR3_is_located_in_Oven(Oven.EMPTY);
        form.setR3_features_Beeper(Beeper.EMPTY);
    }

    public void relate_R4_Oven_is_accessed_via_Door(Oven form, Door part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot relate empty instances.");
        part.setR4_provides_access_to_Oven(form);
        form.setR4_is_accessed_via_Door(part);
        form.setDoorID(part.getDoorID());
    }

    public void unrelate_R4_Oven_is_accessed_via_Door(Oven form, Door part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot unrelate empty instances.");
        part.setR4_provides_access_to_Oven(Oven.EMPTY);
        form.setR4_is_accessed_via_Door(Door.EMPTY);
    }

    public void relate_R5_Oven_has_Turntable(Oven form, Turntable part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot relate empty instances.");
        part.setR5_occupies_Oven(form);
        form.setR5_has_Turntable(part);
        form.setTurntableID(part.getTurntableID());
    }

    public void unrelate_R5_Oven_has_Turntable(Oven form, Turntable part) {
        if (form.isEmpty() || part.isEmpty())
            throw new EmptyInstanceException("Cannot unrelate empty instances.");
        part.setR5_occupies_Oven(Oven.EMPTY);
        form.setR5_has_Turntable(Turntable.EMPTY);
    }

    // instance selections
    public Beeper.Set Beeper_instances() {
        return new Beeper.Set(getAllInstances(Beeper.class));
    }

    public Door.Set Door_instances() {
        return new Door.Set(getAllInstances(Door.class));
    }

    public InternalLight.Set InternalLight_instances() {
        return new InternalLight.Set(getAllInstances(InternalLight.class));
    }

    public MagnetronTube.Set MagnetronTube_instances() {
        return new MagnetronTube.Set(getAllInstances(MagnetronTube.class));
    }

    public Oven.Set Oven_instances() {
        return new Oven.Set(getAllInstances(Oven.class));
    }

    public TestSequences.Set TestSequences_instances() {
        return new TestSequences.Set(getAllInstances(TestSequences.class));
    }

    public Turntable.Set Turntable_instances() {
        return new Turntable.Set(getAllInstances(Turntable.class));
    }

    // utilities
    private ARCH TerminatorARCH;

    public ARCH ARCH() {
        if (TerminatorARCH == null)
            TerminatorARCH = new ARCH(this);
        return TerminatorARCH;
    }

    private CP TerminatorCP;

    public CP CP() {
        if (TerminatorCP == null)
            TerminatorCP = new CP(this);
        return TerminatorCP;
    }

    private CMD TerminatorCMD;

    public CMD CMD() {
        if (TerminatorCMD == null)
            TerminatorCMD = new io.ciera.runtime.util.CMD(this);
        return TerminatorCMD;
    }

    private TIM TerminatorTIM;

    public TIM TIM() {
        if (TerminatorTIM == null)
            TerminatorTIM = new TIM(this);
        return TerminatorTIM;
    }

    // component initialization function
    @Override
    public void initialize() {
        init();
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

}
