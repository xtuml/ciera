package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface IBinaryRelationship extends IRelationship {

    public UniqueId getOne();
    public UniqueId getOther();
}
