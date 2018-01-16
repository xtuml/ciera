package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface IAssociativeRelationship extends IRelationship {

    public UniqueId getOne();
    public UniqueId getOther();
    public UniqueId getLink();
}
