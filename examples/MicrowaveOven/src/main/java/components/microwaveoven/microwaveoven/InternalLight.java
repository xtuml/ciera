package components.microwaveoven.microwaveoven;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Oven;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface InternalLight extends IModelInstance<InternalLight,MicrowaveOven> {

    // attributes
    public UniqueId getLightID() throws XtumlException;
    public void setLightID( UniqueId m_LightID ) throws XtumlException;


    // operations


    // selections
    default public void setR2_illuminates_Oven( Oven inst ) {}
    public Oven R2_illuminates_Oven() throws XtumlException;


}
