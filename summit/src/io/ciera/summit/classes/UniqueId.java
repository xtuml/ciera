package io.ciera.summit.classes;

import io.ciera.summit.exceptions.UniqueIdException;
import io.ciera.summit.exceptions.XtumlException;

public class UniqueId implements Comparable<UniqueId> {
	
	private static int current_id = Integer.MIN_VALUE + 1;
	private static final int NULL_UNIQUE_ID = Integer.MIN_VALUE;
	
	private int id;
	
	public UniqueId() {
		try {
			id = getNext();
		} catch ( XtumlException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void nullify() {
		id = NULL_UNIQUE_ID;
	}
	
	public boolean isNull() {
		return id == NULL_UNIQUE_ID;
	}
	
	protected int getIdValue() {
		return id;
	}

	@Override
	public int compareTo( UniqueId uid ) {
		if ( id < uid.getIdValue() ) return -1;
		else if ( id == uid.getIdValue() ) return 0;
		else return 1;
	}
	
	@Override
	public boolean equals( Object uid ) {
		if ( uid instanceof UniqueId ) {
			return id == ((UniqueId)uid).getIdValue();
		}
		else return false;
	}
	
	@Override
	public String toString() {
		return Integer.toUnsignedString( id );
	}
	
	private synchronized static int getNext() throws XtumlException {
		if ( Integer.MAX_VALUE == current_id ) throw new UniqueIdException( "Unique ID overflow detected" );
		return current_id++;
	}
	
}
