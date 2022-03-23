package io.ciera.runtime.api.types;

import java.io.EOFException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import io.ciera.runtime.api.exceptions.DeviceReadException;

public class ReadableDevice extends Device {

    private Scanner sc;

    public ReadableDevice(String name, InputStream in) {
        super(name);
        this.sc = new Scanner(in);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Object> T read(final Class<T> cls) {
        if (sc.hasNext()) {
            String token = sc.next();
            try {
                Method deserialize = cls.getMethod("fromString", String.class);
                return (T) deserialize.invoke(null, token);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new DeviceReadException(
                        "Could not deserialize token '" + token + "' for type '" + cls.getName() + "'", e, this);
            }

        } else {
            throw new DeviceReadException("Could not get next token from device", new EOFException(), this);
        }
    }

    @Override
    public String readLine() {
        if (sc.hasNextLine()) {
            return sc.nextLine();
        } else {
            throw new DeviceReadException("Could not get next line from device", new EOFException(), this);
        }
    }

    @Override
    public void write(Object o) {
        throw new UnsupportedOperationException("Cannot write to read-only device");
    }

    @Override
    public void writeLine(Object o) {
        throw new UnsupportedOperationException("Cannot write to read-only device");
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Cannot flush read-only device");
    }

}
