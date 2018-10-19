package io.ciera.cairn.classes;

import java.util.Iterator;
import java.util.List;

import io.ciera.summit.classes.IInstanceIdentifier;
import io.ciera.summit.exceptions.XtumlException;

public abstract class InstanceIdentifier implements IInstanceIdentifier {
    
    @Override
    public boolean equals( Object o ) {
        if ( null != o && o instanceof IInstanceIdentifier && getElements().size() == ((IInstanceIdentifier)o).getElements().size() ) {
            Iterator<Object> otherElements = ((IInstanceIdentifier)o).getElements().iterator();
            for ( Object element : getElements() ) {
                if ( !element.equals(otherElements.next()) ) return false;
            }
            return true;
        }
        else return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        for ( Object element : getElements() ) {
            hash = hash * 31 + element.hashCode();
        }
        return hash;
    }
    
    public static IInstanceIdentifier getIdentifier( ElementList list ) {
        return new InstanceIdentifier() {
            @Override
            public List<Object> getElements() {
                try {
                    return list.elementList();
                }
                catch ( XtumlException e ) {
                    e.printStackTrace();
                    System.exit(1);
                    return null;
                }
            }
        };
    }
    
    public static interface ElementList {
        public List<Object> elementList() throws XtumlException;
    }

}