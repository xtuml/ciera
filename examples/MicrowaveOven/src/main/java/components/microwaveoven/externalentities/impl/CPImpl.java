package components.microwaveoven.externalentities.impl;


import components.microwaveoven.externalentities.CP;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.Utility;


public class CPImpl<C extends IComponent<C>> extends Utility<C> implements CP {

    public CPImpl( C context ) {
        super( context );
    }



}
