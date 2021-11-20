package io.ciera.runtime.summit2.types;

public abstract class Device extends ModelType {

    private static Device console = null;

    public abstract Object read(final Class<Object> cls);

    public abstract String readLine();

    public abstract void write(Object o);

    public abstract void writeLine(Object o);

    public static Device console() {
        if (console == null) {
            console = new ReadWriteDevice(System.in, System.out);
        }
        return console;
    }

}