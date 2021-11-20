package io.ciera.runtime.summit2.types;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ReadWriteDevice extends ReadableDevice {

    private PrintStream out;

    public ReadWriteDevice(InputStream in, OutputStream out) {
        super(in);
        this.out = new PrintStream(out);
    }

    @Override
    public void write(Object o) {
        out.print(o);
    }

    @Override
    public void writeLine(Object o) {
        write(o);
        out.println();
    }

}