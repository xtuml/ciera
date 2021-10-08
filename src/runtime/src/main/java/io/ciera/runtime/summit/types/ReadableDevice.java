package io.ciera.runtime.summit.types;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class ReadableDevice extends BaseDevice implements IXtumlType, Device {

    private Scanner sc;

    public ReadableDevice(InputStream in) {
        this.sc = new Scanner(in);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IXtumlType> T read(final Class<T> cls) throws XtumlException {
        if (sc.hasNext()) {
            String token = sc.next();
            try {
                Method deserialize = cls.getMethod("deserialize", Object.class);
                return (T) deserialize.invoke(null, token);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new XtumlException(
                        "Could not deserialize token '" + token + "' for type '" + cls.getName() + "'");
            }

        } else {
            throw new XtumlException("Could not read token from device");
        }
    }

    @Override
    public String readLine() throws XtumlException {
        if (sc.hasNextLine()) {
            return sc.nextLine();
        } else {
            throw new XtumlException("Could not read token from device");
        }
    }

}