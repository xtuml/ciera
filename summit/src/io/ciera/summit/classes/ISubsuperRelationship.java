package io.ciera.summit.classes;

import io.ciera.summit.util.UniqueId;

public interface ISubsuperRelationship extends IRelationship {

    public UniqueId getSupertype();
    public UniqueId getSubtype();
}
