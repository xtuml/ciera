package io.ciera.runtime.summit.types;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import io.ciera.runtime.summit.exceptions.XtumlException;

public class ReadWriteDevice extends ReadableDevice implements IXtumlType {

    private PrintStream out;

    public ReadWriteDevice(InputStream in, OutputStream out) {
        super(in);
        this.out = new PrintStream(out);
    }

    @Override
    public void write(Object o) throws XtumlException {
        if (o instanceof IXtumlType) {
            out.print(((IXtumlType) o).serialize());
        } else {
            out.print(o);
        }
    }

    @Override
    public void writeLine(Object o) throws XtumlException {
        write(o);
        out.println();
    }

}