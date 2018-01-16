package io.ciera.cairn.classes;

import io.ciera.summit.classes.ISubsuperRelationship;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.IRelationshipSet;

public abstract class SubsuperRelationship implements ISubsuperRelationship {

    @Override
    public boolean equals( Object obj ) {
        if ( null != obj && obj instanceof ISubsuperRelationship ) {
        	return getNumber() == ((IRelationship)obj).getNumber() &&
        		   getSupertype().equals( ((ISubsuperRelationship)obj).getSupertype() ) &&
        	       getSubtype().equals( ((ISubsuperRelationship)obj).getSubtype() );
        }
        else return false;
    }
    
    @Override
    public int hashCode() {
    	int hash = getNumber();
    	hash = 31 * hash + getSupertype().hashCode();
    	hash = 31 * hash + getSubtype().hashCode();
    	return hash;
    }
    
    @Override
    public IRelationshipSet toSet() {
    	IRelationshipSet newSet = new SubsuperRelationshipSet( getNumber() );
    	newSet.add( this );
    	return newSet;
    }

}
