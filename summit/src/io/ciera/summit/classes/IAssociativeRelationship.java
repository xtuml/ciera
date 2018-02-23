package io.ciera.summit.classes;

public interface IAssociativeRelationship extends IRelationship {

    public UniqueId getOne();
    public UniqueId getOther();
    public UniqueId getLink();
}
