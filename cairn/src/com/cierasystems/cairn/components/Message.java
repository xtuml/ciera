package com.cierasystems.cairn.components;

import java.util.ArrayList;
import java.util.List;

import com.cierasystems.summit.components.IMessage;

public class Message implements IMessage {
	private static final long serialVersionUID = 1L;

	private String name;
	private String target;
	private String origin;
	private List<Object> data;
	
	public Message( String name, String target, String origin, Object ... data ) {
		this.name = name;
		this.target = target;
		this.origin = origin;
		this.data = new ArrayList<Object>();
		for ( Object dataItem : data ) {
			this.data.add( dataItem );
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public String getOrigin() {
		return origin;
	}

	@Override
	public Object getData( int index ) {
		try {
			return data.get( index );
		}
		catch ( IndexOutOfBoundsException e ) {
			return null;
		}
	}
	
}