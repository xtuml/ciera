package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface Device {

    public <T extends IXtumlType> T read(final Class<T> cls) throws XtumlException;
    public String readLine() throws XtumlException;
    public void write(Object o) throws XtumlException;
    public void writeLine(Object o) throws XtumlException;

}