package io.ciera.summit.components;

import java.io.Serializable;

public interface IMessage extends Serializable {
	
	public String getName();
	public String getTarget();
	public String getOrigin();
	public Object getData( int index );

}
