package components.microwaveoven.microwaveoven;


import components.microwaveoven.microwaveoven.OvenSet;

import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.types.UniqueId;


public interface BeeperSet extends IInstanceSet<BeeperSet,Beeper> {

    // attributes
    public void setBeeper_timer( TimerHandle m_beeper_timer ) throws XtumlException;
    public void setBeep_count( int m_beep_count ) throws XtumlException;
    public void setBeeperID( UniqueId m_BeeperID ) throws XtumlException;


    // selections
    public OvenSet R3_is_located_in_Oven() throws XtumlException;


}
