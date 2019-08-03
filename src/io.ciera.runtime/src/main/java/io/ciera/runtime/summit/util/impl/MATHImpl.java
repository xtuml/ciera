package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.MATH;
import io.ciera.runtime.summit.util.Utility;

public class MATHImpl<C extends IComponent<C>> extends Utility<C> implements MATH {

    public MATHImpl(C context) {
        super(context);
    }

	@Override
	public double sqrt(double p_x) throws XtumlException {
		return Math.sqrt(p_x);
	}

}
