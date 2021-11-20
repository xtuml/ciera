package io.ciera.runtime.summit2.types;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import io.ciera.runtime.summit2.exceptions.DeserializationException;
import io.ciera.runtime.summit2.exceptions.DeviceReadException;

public class ReadableDevice extends Device {

    private Scanner sc;

    public ReadableDevice(InputStream in) {
        this.sc = new Scanner(in);
    }

    @Override
    public Object read(final Class<Object> cls) {
        if (sc.hasNext()) {
            String token = sc.next();
            try {
                Method deserialize = cls.getMethod("fromString", Object.class);
                return deserialize.invoke(null, token);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new DeserializationException(
                        "Could not deserialize token '" + token + "' for type '" + cls.getName() + "'");
            }

        } else {
            throw new DeviceReadException("Could not read token from device");
        }
    }

    @Override
    public String readLine() {
        if (sc.hasNextLine()) {
            return sc.nextLine();
        } else {
            throw new DeviceReadException("Could not read token from device");
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

}