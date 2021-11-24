package components.microwaveoven.microwaveoven.impl;


import components.microwaveoven.microwaveoven.OvenSet;
import components.microwaveoven.microwaveoven.Turntable;
import components.microwaveoven.microwaveoven.TurntableSet;
import components.microwaveoven.microwaveoven.impl.OvenSetImpl;

import io.ciera.runtime.summit.classes.InstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class TurntableSetImpl extends InstanceSet<TurntableSet,Turntable> implements TurntableSet {

    public TurntableSetImpl() {
    }

    public TurntableSetImpl(Comparator<? super Turntable> comp) {
        super(comp);
    }

    // attributes
    @Override
    public void setTurntableID( UniqueId m_TurntableID ) throws XtumlException {
        for ( Turntable turntable : this ) turntable.setTurntableID( m_TurntableID );
    }


    // selections
    @Override
    public OvenSet R5_occupies_Oven() throws XtumlException {
        OvenSet ovenset = new OvenSetImpl();
        for ( Turntable turntable : this ) ovenset.add( turntable.R5_occupies_Oven() );
        return ovenset;
    }


    @Override
    public Turntable nullElement() {
        return TurntableImpl.EMPTY_TURNTABLE;
    }

    @Override
    public TurntableSet emptySet() {
      return new TurntableSetImpl();
    }

    @Override
    public TurntableSet emptySet(Comparator<? super Turntable> comp) {
      return new TurntableSetImpl(comp);
    }

    @Override
    public List<Turntable> elements() {
        return Arrays.asList(toArray(new Turntable[0]));
    }

}
