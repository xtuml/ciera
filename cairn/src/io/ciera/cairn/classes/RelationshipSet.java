package io.ciera.cairn.classes;

import io.ciera.cairn.types.Set;
import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.IRelationshipSet;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.UniqueId;

public class RelationshipSet extends Set<IRelationshipSet,IRelationship> implements IRelationshipSet {

    @Override
    public IRelationshipSet getFormalizing( UniqueId part ) throws XtumlException {
        return where( selected -> selected.getPart().equals( part ) );
    }

    @Override
    public IRelationshipSet getParticipating( UniqueId form ) throws XtumlException {
        return where( selected -> selected.getForm().equals( form ) );
    }

    @Override
    public IRelationship get( UniqueId form, UniqueId part ) throws XtumlException {
        return where( selected -> selected.getForm().equals( form ) && selected.getPart().equals( part ) ).any();
    }

    @Override
    public IRelationship nullElement() {
        return null;
    }

    @Override
    public IRelationshipSet emptySet() {
        return new RelationshipSet();
    }

}
