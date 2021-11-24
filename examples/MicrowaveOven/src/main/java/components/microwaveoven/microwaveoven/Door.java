package components.microwaveoven.microwaveoven;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Oven;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface Door extends IModelInstance<Door,MicrowaveOven> {

    // attributes
    public void setDoorID( UniqueId m_DoorID ) throws XtumlException;
    public UniqueId getDoorID() throws XtumlException;
    public boolean getIs_secure() throws XtumlException;
    public void setIs_secure( boolean m_is_secure ) throws XtumlException;


    // operations


    // selections
    default public void setR4_provides_access_to_Oven( Oven inst ) {}
    public Oven R4_provides_access_to_Oven() throws XtumlException;


}
