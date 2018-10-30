package io.ciera.runtime.summit.classes;

import java.util.Arrays;
import java.util.List;

import io.ciera.runtime.summit.classes.RelationshipSet;
import io.ciera.runtime.summit.types.Set;
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
    
    @Override
    public RelationshipSet value() {
        return this;
    }
    
    @Override
    public List<IRelationship> elements() {
        return Arrays.asList(toArray(new IRelationship[0]));
    }
    
}
