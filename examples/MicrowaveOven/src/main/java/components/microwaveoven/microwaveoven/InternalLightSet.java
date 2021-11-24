package components.microwaveoven.microwaveoven;


import components.microwaveoven.microwaveoven.OvenSet;

import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface InternalLightSet extends IInstanceSet<InternalLightSet,InternalLight> {

    // attributes
    public void setLightID( UniqueId m_LightID ) throws XtumlException;


    // selections
    public OvenSet R2_illuminates_Oven() throws XtumlException;


}
