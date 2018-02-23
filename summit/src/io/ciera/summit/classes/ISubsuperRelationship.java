package io.ciera.summit.classes;

public interface ISubsuperRelationship extends IRelationship {

    public UniqueId getSupertype();
    public UniqueId getSubtype();
}
