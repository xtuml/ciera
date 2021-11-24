package components.microwaveoven.microwaveoven;


import components.microwaveoven.datatypes.Tube_Wattage;
import components.microwaveoven.microwaveoven.OvenSet;

import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface MagnetronTubeSet extends IInstanceSet<MagnetronTubeSet,MagnetronTube> {

    // attributes
    public void setCurrent_power_output( Tube_Wattage m_current_power_output ) throws XtumlException;
    public void setTubeID( UniqueId m_TubeID ) throws XtumlException;


    // selections
    public OvenSet R1_is_housed_in_Oven() throws XtumlException;


}
