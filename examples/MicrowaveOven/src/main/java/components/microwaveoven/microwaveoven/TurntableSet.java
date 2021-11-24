package components.microwaveoven.microwaveoven;


import components.microwaveoven.microwaveoven.OvenSet;

import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface TurntableSet extends IInstanceSet<TurntableSet,Turntable> {

    // attributes
    public void setTurntableID( UniqueId m_TurntableID ) throws XtumlException;


    // selections
    public OvenSet R5_occupies_Oven() throws XtumlException;


}
