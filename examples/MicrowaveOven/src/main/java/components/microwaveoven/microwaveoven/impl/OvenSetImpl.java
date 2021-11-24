package components.microwaveoven.microwaveoven.impl;


import components.microwaveoven.microwaveoven.BeeperSet;
import components.microwaveoven.microwaveoven.DoorSet;
import components.microwaveoven.microwaveoven.InternalLightSet;
import components.microwaveoven.microwaveoven.MagnetronTubeSet;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.OvenSet;
import components.microwaveoven.microwaveoven.TurntableSet;
import components.microwaveoven.microwaveoven.impl.BeeperSetImpl;
import components.microwaveoven.microwaveoven.impl.DoorSetImpl;
import components.microwaveoven.microwaveoven.impl.InternalLightSetImpl;
import components.microwaveoven.microwaveoven.impl.MagnetronTubeSetImpl;
import components.microwaveoven.microwaveoven.impl.TurntableSetImpl;

import io.ciera.runtime.summit.classes.InstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.types.UniqueId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class OvenSetImpl extends InstanceSet<OvenSet,Oven> implements OvenSet {

    public OvenSetImpl() {
    }

    public OvenSetImpl(Comparator<? super Oven> comp) {
        super(comp);
    }

    // attributes
    @Override
    public void setLightID( UniqueId ref_LightID ) throws XtumlException {
        for ( Oven oven : this ) oven.setLightID( ref_LightID );
    }
    @Override
    public void setOven_timer( TimerHandle m_oven_timer ) throws XtumlException {
        for ( Oven oven : this ) oven.setOven_timer( m_oven_timer );
    }
    @Override
    public void setRemaining_cooking_time( int m_remaining_cooking_time ) throws XtumlException {
        for ( Oven oven : this ) oven.setRemaining_cooking_time( m_remaining_cooking_time );
    }
    @Override
    public void setTubeID( UniqueId ref_TubeID ) throws XtumlException {
        for ( Oven oven : this ) oven.setTubeID( ref_TubeID );
    }
    @Override
    public void setDoorID( UniqueId ref_DoorID ) throws XtumlException {
        for ( Oven oven : this ) oven.setDoorID( ref_DoorID );
    }
    @Override
    public void setOvenID( UniqueId m_OvenID ) throws XtumlException {
        for ( Oven oven : this ) oven.setOvenID( m_OvenID );
    }
    @Override
    public void setBeeperID( UniqueId ref_BeeperID ) throws XtumlException {
        for ( Oven oven : this ) oven.setBeeperID( ref_BeeperID );
    }
    @Override
    public void setTurntableID( UniqueId ref_TurntableID ) throws XtumlException {
        for ( Oven oven : this ) oven.setTurntableID( ref_TurntableID );
    }


    // selections
    @Override
    public MagnetronTubeSet R1_houses_MagnetronTube() throws XtumlException {
        MagnetronTubeSet magnetrontubeset = new MagnetronTubeSetImpl();
        for ( Oven oven : this ) magnetrontubeset.add( oven.R1_houses_MagnetronTube() );
        return magnetrontubeset;
    }
    @Override
    public InternalLightSet R2_is_illuminated_by_InternalLight() throws XtumlException {
        InternalLightSet internallightset = new InternalLightSetImpl();
        for ( Oven oven : this ) internallightset.add( oven.R2_is_illuminated_by_InternalLight() );
        return internallightset;
    }
    @Override
    public BeeperSet R3_features_Beeper() throws XtumlException {
        BeeperSet beeperset = new BeeperSetImpl();
        for ( Oven oven : this ) beeperset.add( oven.R3_features_Beeper() );
        return beeperset;
    }
    @Override
    public DoorSet R4_is_accessed_via_Door() throws XtumlException {
        DoorSet doorset = new DoorSetImpl();
        for ( Oven oven : this ) doorset.add( oven.R4_is_accessed_via_Door() );
        return doorset;
    }
    @Override
    public TurntableSet R5_has_Turntable() throws XtumlException {
        TurntableSet turntableset = new TurntableSetImpl();
        for ( Oven oven : this ) turntableset.add( oven.R5_has_Turntable() );
        return turntableset;
    }


    @Override
    public Oven nullElement() {
        return OvenImpl.EMPTY_OVEN;
    }

    @Override
    public OvenSet emptySet() {
      return new OvenSetImpl();
    }

    @Override
    public OvenSet emptySet(Comparator<? super Oven> comp) {
      return new OvenSetImpl(comp);
    }

    @Override
    public List<Oven> elements() {
        return Arrays.asList(toArray(new Oven[0]));
    }

}
