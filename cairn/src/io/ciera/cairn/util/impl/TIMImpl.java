package io.ciera.cairn.util.impl;

import io.ciera.cairn.util.TIM;
import io.ciera.cairn.util.Utility;
import io.ciera.summit.components.IComponent;

public class TIMImpl<C extends IComponent<C>> extends Utility<C> implements TIM {

	public TIMImpl( C population ) {
		super( population );
	}

}
