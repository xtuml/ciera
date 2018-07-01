package io.ciera.cairn.classes;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import io.ciera.summit.classes.IInstanceIdentifier;

public class InstanceIdentifier implements IInstanceIdentifier {
    
    List<Object> elements;
    
    public InstanceIdentifier( Object ... elements ) {
        this.elements = Arrays.asList(elements);
    }

    @Override
    public List<Object> getElements() {
        return elements;
    }
    
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
    
}