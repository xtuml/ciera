package io.ciera.summit.classes;

public interface IBinaryRelationship extends IRelationship {

    public UniqueId getOne();
    public UniqueId getOther();
}
