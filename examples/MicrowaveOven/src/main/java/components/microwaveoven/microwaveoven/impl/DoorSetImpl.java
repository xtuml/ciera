package components.microwaveoven.microwaveoven.impl;


import components.microwaveoven.microwaveoven.Door;
import components.microwaveoven.microwaveoven.DoorSet;
import components.microwaveoven.microwaveoven.OvenSet;
import components.microwaveoven.microwaveoven.impl.OvenSetImpl;

import io.ciera.runtime.summit.classes.InstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class DoorSetImpl extends InstanceSet<DoorSet,Door> implements DoorSet {

    public DoorSetImpl() {
    }

    public DoorSetImpl(Comparator<? super Door> comp) {
        super(comp);
    }

    // attributes
    @Override
    public void setDoorID( UniqueId m_DoorID ) throws XtumlException {
        for ( Door door : this ) door.setDoorID( m_DoorID );
    }
    @Override
    public void setIs_secure( boolean m_is_secure ) throws XtumlException {
        for ( Door door : this ) door.setIs_secure( m_is_secure );
    }


    // selections
    @Override
    public OvenSet R4_provides_access_to_Oven() throws XtumlException {
        OvenSet ovenset = new OvenSetImpl();
        for ( Door door : this ) ovenset.add( door.R4_provides_access_to_Oven() );
        return ovenset;
    }


    @Override
    public Door nullElement() {
        return DoorImpl.EMPTY_DOOR;
    }

    @Override
    public DoorSet emptySet() {
      return new DoorSetImpl();
    }

    @Override
    public DoorSet emptySet(Comparator<? super Door> comp) {
      return new DoorSetImpl(comp);
    }

    @Override
    public List<Door> elements() {
        return Arrays.asList(toArray(new Door[0]));
    }

}
