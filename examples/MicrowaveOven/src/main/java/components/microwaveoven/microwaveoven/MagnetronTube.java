package components.microwaveoven.microwaveoven;


import components.MicrowaveOven;
import components.microwaveoven.datatypes.Tube_Wattage;
import components.microwaveoven.microwaveoven.Oven;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface MagnetronTube extends IModelInstance<MagnetronTube,MicrowaveOven> {

    // attributes
    public UniqueId getTubeID() throws XtumlException;
    public void setTubeID( UniqueId m_TubeID ) throws XtumlException;
    public void setCurrent_power_output( Tube_Wattage m_current_power_output ) throws XtumlException;
    public Tube_Wattage getCurrent_power_output() throws XtumlException;


    // operations


    // selections
    default public void setR1_is_housed_in_Oven( Oven inst ) {}
    public Oven R1_is_housed_in_Oven() throws XtumlException;


}
