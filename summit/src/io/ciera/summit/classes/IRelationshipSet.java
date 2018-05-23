package io.ciera.summit.classes;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.ISet;
import io.ciera.summit.types.IUniqueId;

public interface IRelationshipSet extends ISet<IRelationshipSet,IRelationship> {

    public IRelationshipSet getFormalizing( IUniqueId part ) throws XtumlException;
    public IRelationshipSet getParticipating( IUniqueId form ) throws XtumlException;
    public IRelationship get( IUniqueId form, IUniqueId part ) throws XtumlException;

}
