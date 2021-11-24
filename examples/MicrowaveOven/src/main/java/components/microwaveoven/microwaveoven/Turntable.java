package components.microwaveoven.microwaveoven;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Oven;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface Turntable extends IModelInstance<Turntable,MicrowaveOven> {

    // attributes
    public void setTurntableID( UniqueId m_TurntableID ) throws XtumlException;
    public UniqueId getTurntableID() throws XtumlException;


    // operations


    // selections
    default public void setR5_occupies_Oven( Oven inst ) {}
    public Oven R5_occupies_Oven() throws XtumlException;


}
