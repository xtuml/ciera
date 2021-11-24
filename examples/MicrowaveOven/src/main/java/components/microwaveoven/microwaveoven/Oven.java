package components.microwaveoven.microwaveoven;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Beeper;
import components.microwaveoven.microwaveoven.Door;
import components.microwaveoven.microwaveoven.InternalLight;
import components.microwaveoven.microwaveoven.MagnetronTube;
import components.microwaveoven.microwaveoven.Turntable;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.types.UniqueId;


public interface Oven extends IModelInstance<Oven,MicrowaveOven> {

    // attributes
    public void setOvenID( UniqueId m_OvenID ) throws XtumlException;
    public UniqueId getOvenID() throws XtumlException;
    public UniqueId getTubeID() throws XtumlException;
    public void setTubeID( UniqueId ref_TubeID ) throws XtumlException;
    public void setLightID( UniqueId ref_LightID ) throws XtumlException;
    public UniqueId getLightID() throws XtumlException;
    public UniqueId getBeeperID() throws XtumlException;
    public void setBeeperID( UniqueId ref_BeeperID ) throws XtumlException;
    public void setDoorID( UniqueId ref_DoorID ) throws XtumlException;
    public UniqueId getDoorID() throws XtumlException;
    public void setTurntableID( UniqueId ref_TurntableID ) throws XtumlException;
    public UniqueId getTurntableID() throws XtumlException;
    public void setOven_timer( TimerHandle m_oven_timer ) throws XtumlException;
    public TimerHandle getOven_timer() throws XtumlException;
    public int getRemaining_cooking_time() throws XtumlException;
    public void setRemaining_cooking_time( int m_remaining_cooking_time ) throws XtumlException;


    // operations


    // selections
    default public void setR1_houses_MagnetronTube( MagnetronTube inst ) {}
    public MagnetronTube R1_houses_MagnetronTube() throws XtumlException;
    default public void setR2_is_illuminated_by_InternalLight( InternalLight inst ) {}
    public InternalLight R2_is_illuminated_by_InternalLight() throws XtumlException;
    default public void setR3_features_Beeper( Beeper inst ) {}
    public Beeper R3_features_Beeper() throws XtumlException;
    default public void setR4_is_accessed_via_Door( Door inst ) {}
    public Door R4_is_accessed_via_Door() throws XtumlException;
    default public void setR5_has_Turntable( Turntable inst ) {}
    public Turntable R5_has_Turntable() throws XtumlException;


}
