package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.NotImplementedException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public class BaseDevice implements IXtumlType, Device {
    
    private static Device console = null;

    @Override
    public <T extends IXtumlType> T read(final Class<T> cls) throws XtumlException {
        throw new NotImplementedException("Device does not support reading.");
    }

    @Override
    public String readLine() throws XtumlException {
        throw new NotImplementedException("Device does not support reading.");
    }

    @Override
    public void write(Object o) throws XtumlException {
        throw new NotImplementedException("Device does not support writing.");
    }

    @Override
    public void writeLine(Object o) throws XtumlException {
        throw new NotImplementedException("Device does not support writing.");
    }

    @Override
    public String serialize() {
        return toString();
    }
    
    public static Device console() {
        if (console == null) {
            console = new ReadWriteDevice(System.in, System.out);
        }
        return console;
    }

}