package components.microwaveoven.microwaveoven.impl;


import components.microwaveoven.microwaveoven.InternalLight;
import components.microwaveoven.microwaveoven.InternalLightSet;
import components.microwaveoven.microwaveoven.OvenSet;
import components.microwaveoven.microwaveoven.impl.OvenSetImpl;

import io.ciera.runtime.summit.classes.InstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class InternalLightSetImpl extends InstanceSet<InternalLightSet,InternalLight> implements InternalLightSet {

    public InternalLightSetImpl() {
    }

    public InternalLightSetImpl(Comparator<? super InternalLight> comp) {
        super(comp);
    }

    // attributes
    @Override
    public void setLightID( UniqueId m_LightID ) throws XtumlException {
        for ( InternalLight internallight : this ) internallight.setLightID( m_LightID );
    }


    // selections
    @Override
    public OvenSet R2_illuminates_Oven() throws XtumlException {
        OvenSet ovenset = new OvenSetImpl();
        for ( InternalLight internallight : this ) ovenset.add( internallight.R2_illuminates_Oven() );
        return ovenset;
    }


    @Override
    public InternalLight nullElement() {
        return InternalLightImpl.EMPTY_INTERNALLIGHT;
    }

    @Override
    public InternalLightSet emptySet() {
      return new InternalLightSetImpl();
    }

    @Override
    public InternalLightSet emptySet(Comparator<? super InternalLight> comp) {
      return new InternalLightSetImpl(comp);
    }

    @Override
    public List<InternalLight> elements() {
        return Arrays.asList(toArray(new InternalLight[0]));
    }

}
