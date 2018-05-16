package io.ciera.summit.classes;

import io.ciera.summit.exceptions.XtumlException;

public interface IWhere {
	
	public boolean evaluate( IModelInstance selected ) throws XtumlException;

}
