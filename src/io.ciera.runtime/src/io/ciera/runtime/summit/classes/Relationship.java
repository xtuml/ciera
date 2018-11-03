package io.ciera.runtime.summit.classes;

import io.ciera.runtime.summit.types.UniqueId;

public class Relationship implements IRelationship, Comparable<IRelationship> {

    private UniqueId form;
    private UniqueId part;

    public Relationship(UniqueId form, UniqueId part) {
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
    public boolean equals(Object obj) {
        if (null != obj && obj instanceof IRelationship) {
            return getForm().equals(((IRelationship) obj).getForm())
                    && getPart().equals(((IRelationship) obj).getPart());
        } else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = getForm().hashCode();
        hash = hash * 31 + getPart().hashCode();
        return hash;
    }

    @Override
    public int compareTo(IRelationship r) {
        if (null == r)
            return 1;
        else {
            int comp1 = form.compareTo(r.getForm());
            if (0 == comp1)
                return part.compareTo(r.getPart());
            else
                return comp1;
        }
    }

}
