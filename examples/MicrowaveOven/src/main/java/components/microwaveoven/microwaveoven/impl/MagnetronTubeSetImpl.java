package components.microwaveoven.microwaveoven.impl;


import components.microwaveoven.datatypes.Tube_Wattage;
import components.microwaveoven.microwaveoven.MagnetronTube;
import components.microwaveoven.microwaveoven.MagnetronTubeSet;
import components.microwaveoven.microwaveoven.OvenSet;
import components.microwaveoven.microwaveoven.impl.OvenSetImpl;

import io.ciera.runtime.summit.classes.InstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class MagnetronTubeSetImpl extends InstanceSet<MagnetronTubeSet,MagnetronTube> implements MagnetronTubeSet {

    public MagnetronTubeSetImpl() {
    }

    public MagnetronTubeSetImpl(Comparator<? super MagnetronTube> comp) {
        super(comp);
    }

    // attributes
    @Override
    public void setCurrent_power_output( Tube_Wattage m_current_power_output ) throws XtumlException {
        for ( MagnetronTube magnetrontube : this ) magnetrontube.setCurrent_power_output( m_current_power_output );
    }
    @Override
    public void setTubeID( UniqueId m_TubeID ) throws XtumlException {
        for ( MagnetronTube magnetrontube : this ) magnetrontube.setTubeID( m_TubeID );
    }


    // selections
    @Override
    public OvenSet R1_is_housed_in_Oven() throws XtumlException {
        OvenSet ovenset = new OvenSetImpl();
        for ( MagnetronTube magnetrontube : this ) ovenset.add( magnetrontube.R1_is_housed_in_Oven() );
        return ovenset;
    }


    @Override
    public MagnetronTube nullElement() {
        return MagnetronTubeImpl.EMPTY_MAGNETRONTUBE;
    }

    @Override
    public MagnetronTubeSet emptySet() {
      return new MagnetronTubeSetImpl();
    }

    @Override
    public MagnetronTubeSet emptySet(Comparator<? super MagnetronTube> comp) {
      return new MagnetronTubeSetImpl(comp);
    }

    @Override
    public List<MagnetronTube> elements() {
        return Arrays.asList(toArray(new MagnetronTube[0]));
    }

}
