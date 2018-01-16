package io.ciera.cairn.classes;

import io.ciera.summit.classes.IBinaryRelationship;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.IRelationshipSet;

public abstract class BinaryRelationship implements IBinaryRelationship {
	
    @Override
    public boolean equals( Object obj ) {
        if ( null != obj && obj instanceof IBinaryRelationship ) {
        	return getNumber() == ((IRelationship)obj).getNumber() &&
        		   getOne().equals( ((IBinaryRelationship)obj).getOne() ) &&
        	       getOther().equals( ((IBinaryRelationship)obj).getOther() );
        }
        else return false;
    }
    
    @Override
    public int hashCode() {
    	int hash = getNumber();
    	hash = 31 * hash + getOne().hashCode();
    	hash = 31 * hash + getOther().hashCode();
    	return hash;
    }
    
    @Override
    public IRelationshipSet toSet() {
    	IRelationshipSet newSet = new BinaryRelationshipSet( getNumber() );
    	newSet.add( this );
    	return newSet;
    }

}
