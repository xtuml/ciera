package io.ciera.runtime.summit.classes;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.ISet;
import io.ciera.runtime.summit.types.UniqueId;

public interface IRelationshipSet extends ISet<IRelationshipSet, IRelationship> {

    public IRelationshipSet getFormalizing(UniqueId part) throws XtumlException;

    public IRelationshipSet getParticipating(UniqueId form) throws XtumlException;

    public IRelationship get(UniqueId form, UniqueId part) throws XtumlException;

}
