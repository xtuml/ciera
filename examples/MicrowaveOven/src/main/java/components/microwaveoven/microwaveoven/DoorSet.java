package components.microwaveoven.microwaveoven;


import components.microwaveoven.microwaveoven.OvenSet;

import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface DoorSet extends IInstanceSet<DoorSet,Door> {

    // attributes
    public void setDoorID( UniqueId m_DoorID ) throws XtumlException;
    public void setIs_secure( boolean m_is_secure ) throws XtumlException;


    // selections
    public OvenSet R4_provides_access_to_Oven() throws XtumlException;


}
