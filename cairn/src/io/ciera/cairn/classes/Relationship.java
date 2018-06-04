package io.ciera.cairn.classes;

import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.types.UniqueId;

public class Relationship implements IRelationship {

    private UniqueId form;
    private UniqueId part;

    public Relationship( UniqueId form, UniqueId part ) {
        this.form = form;
        this.part = part;
    }

    @Override
    public UniqueId getForm() {
        return form;
    }

    @Override
    public UniqueId getPart() {
        return part;
    }
    
    @Override
    public boolean equals( Object obj ) {
        if ( null != obj && obj instanceof IRelationship ) {
            return getForm().equals( ((IRelationship)obj).getForm() ) &&
                   getPart().equals( ((IRelationship)obj).getPart() );
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = getForm().hashCode();
        hash = hash * 31 + getPart().hashCode();
        return hash;
    }

}
