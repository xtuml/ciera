package io.ciera.summit.types;

public interface IUniqueId extends Comparable<IUniqueId>, IXtumlType<IUniqueId> {

    public void nullify();
    public boolean isNull();

}
