package io.ciera.cairn.classes;

import io.ciera.summit.classes.IAssociativeRelationship;
import io.ciera.summit.classes.IRelationship;

public abstract class AssociativeRelationship implements IAssociativeRelationship {
	
    @Override
    public boolean equals( Object obj ) {
        if ( null != obj && obj instanceof IAssociativeRelationship ) {
        	return getNumber() == ((IRelationship)obj).getNumber() &&
        		   getOne().equals( ((IAssociativeRelationship)obj).getOne() ) &&
        	       getOther().equals( ((IAssociativeRelationship)obj).getOther() ) &&
        	       getLink().equals( ((IAssociativeRelationship)obj).getLink() );
        }
        else return false;
    }
    
    @Override
    public int hashCode() {
    	int hash = getNumber();
    	hash = 31 * hash + getOne().hashCode();
    	hash = 31 * hash + getOther().hashCode();
    	hash = 31 * hash + getLink().hashCode();
    	return hash;
    }

}
