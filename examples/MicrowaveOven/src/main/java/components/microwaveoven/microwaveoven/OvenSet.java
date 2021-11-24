package components.microwaveoven.microwaveoven;


import components.microwaveoven.microwaveoven.BeeperSet;
import components.microwaveoven.microwaveoven.DoorSet;
import components.microwaveoven.microwaveoven.InternalLightSet;
import components.microwaveoven.microwaveoven.MagnetronTubeSet;
import components.microwaveoven.microwaveoven.TurntableSet;

import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.types.UniqueId;


public interface OvenSet extends IInstanceSet<OvenSet,Oven> {

    // attributes
    public void setLightID( UniqueId ref_LightID ) throws XtumlException;
    public void setOven_timer( TimerHandle m_oven_timer ) throws XtumlException;
    public void setRemaining_cooking_time( int m_remaining_cooking_time ) throws XtumlException;
    public void setTubeID( UniqueId ref_TubeID ) throws XtumlException;
    public void setDoorID( UniqueId ref_DoorID ) throws XtumlException;
    public void setOvenID( UniqueId m_OvenID ) throws XtumlException;
    public void setBeeperID( UniqueId ref_BeeperID ) throws XtumlException;
    public void setTurntableID( UniqueId ref_TurntableID ) throws XtumlException;


    // selections
    public MagnetronTubeSet R1_houses_MagnetronTube() throws XtumlException;
    public InternalLightSet R2_is_illuminated_by_InternalLight() throws XtumlException;
    public BeeperSet R3_features_Beeper() throws XtumlException;
    public DoorSet R4_is_accessed_via_Door() throws XtumlException;
    public TurntableSet R5_has_Turntable() throws XtumlException;


}
