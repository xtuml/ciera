package components.microwaveoven.microwaveoven;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Oven;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.types.UniqueId;


public interface Beeper extends IModelInstance<Beeper,MicrowaveOven> {

    // attributes
    public UniqueId getBeeperID() throws XtumlException;
    public void setBeeperID( UniqueId m_BeeperID ) throws XtumlException;
    public TimerHandle getBeeper_timer() throws XtumlException;
    public void setBeeper_timer( TimerHandle m_beeper_timer ) throws XtumlException;
    public int getBeep_count() throws XtumlException;
    public void setBeep_count( int m_beep_count ) throws XtumlException;


    // operations


    // selections
    default public void setR3_is_located_in_Oven( Oven inst ) {}
    public Oven R3_is_located_in_Oven() throws XtumlException;


}
