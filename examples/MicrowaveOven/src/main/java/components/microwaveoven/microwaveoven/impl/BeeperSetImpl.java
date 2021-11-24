package components.microwaveoven.microwaveoven.impl;


import components.microwaveoven.microwaveoven.Beeper;
import components.microwaveoven.microwaveoven.BeeperSet;
import components.microwaveoven.microwaveoven.OvenSet;
import components.microwaveoven.microwaveoven.impl.OvenSetImpl;

import io.ciera.runtime.summit.classes.InstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.types.UniqueId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class BeeperSetImpl extends InstanceSet<BeeperSet,Beeper> implements BeeperSet {

    public BeeperSetImpl() {
    }

    public BeeperSetImpl(Comparator<? super Beeper> comp) {
        super(comp);
    }

    // attributes
    @Override
    public void setBeeper_timer( TimerHandle m_beeper_timer ) throws XtumlException {
        for ( Beeper beeper : this ) beeper.setBeeper_timer( m_beeper_timer );
    }
    @Override
    public void setBeep_count( int m_beep_count ) throws XtumlException {
        for ( Beeper beeper : this ) beeper.setBeep_count( m_beep_count );
    }
    @Override
    public void setBeeperID( UniqueId m_BeeperID ) throws XtumlException {
        for ( Beeper beeper : this ) beeper.setBeeperID( m_BeeperID );
    }


    // selections
    @Override
    public OvenSet R3_is_located_in_Oven() throws XtumlException {
        OvenSet ovenset = new OvenSetImpl();
        for ( Beeper beeper : this ) ovenset.add( beeper.R3_is_located_in_Oven() );
        return ovenset;
    }


    @Override
    public Beeper nullElement() {
        return BeeperImpl.EMPTY_BEEPER;
    }

    @Override
    public BeeperSet emptySet() {
      return new BeeperSetImpl();
    }

    @Override
    public BeeperSet emptySet(Comparator<? super Beeper> comp) {
      return new BeeperSetImpl(comp);
    }

    @Override
    public List<Beeper> elements() {
        return Arrays.asList(toArray(new Beeper[0]));
    }

}
