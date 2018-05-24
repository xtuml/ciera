package io.ciera.cairn.classes;

import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.types.IUniqueId;

public class Relationship implements IRelationship {

    private IUniqueId form;
    private IUniqueId part;

    public Relationship( IUniqueId form, IUniqueId part ) {
        this.form = form;
        this.part = part;
    }

    @Override
    public IUniqueId getForm() {
        return form;
    }

    @Override
    public IUniqueId getPart() {
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
