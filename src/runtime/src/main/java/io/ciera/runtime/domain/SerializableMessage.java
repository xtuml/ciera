package io.ciera.runtime.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.ciera.runtime.exceptions.DeserializationException;
import io.ciera.runtime.types.UniqueId;

/**
 * The SerializableMessage class extends {@link Message} with the ability to
 * dump and parse the message to a JSON string for the purpose of passing the
 * message across some sort of interface between deployed processes.
 */
public class SerializableMessage extends Message {

    public static final String MESSAGE_HANDLE = "messageHandle";
    public static final String MESSAGE_NAME = "name";
    public static final String MESSAGE_ID = "id";
    public static final String MESSAGE_PARAMETER_DATA = "parameterData";

    public SerializableMessage(Message message) {
        this(message.getMessageHandle(), message.getId(), message.getName(), message.getAll());
    }

    public SerializableMessage(UniqueId messageHandle, int id, String name, Object... data) {
        super(messageHandle, id, name, data);
    }

    public SerializableMessage deserializeData(Class<?>... parameterTypes) {
        Object[] parameterData = getAll();
        if (parameterTypes != null && parameterData.length == parameterTypes.length) {
            for (int i = 0; i < parameterData.length; i++) {
                Class<?> type = parameterTypes[i];
                try {
                    if (int.class.isAssignableFrom(type)) {
                        parameterData[i] = ((Number) parameterData[i]).intValue();
                    } else if (long.class.isAssignableFrom(type)) {
                        parameterData[i] = ((Number) parameterData[i]).longValue();
                    } else if (double.class.isAssignableFrom(type)) {
                        parameterData[i] = ((Number) parameterData[i]).doubleValue();
                    } else if (boolean.class.isAssignableFrom(type)) {
                        parameterData[i] = (boolean) parameterData[i];
                    } else if (Enum.class.isAssignableFrom(type)) {
                        Method loader = type.getMethod("valueOf", Class.class, String.class);
                        parameterData[i] = loader.invoke(null, type, parameterData[i]);
                    } else {
                        Method loader = type.getMethod("fromString", String.class);
                        parameterData[i] = loader.invoke(null, parameterData[i]);
                    }
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    throw new DeserializationException(e);
                }
            }
            return new SerializableMessage(getMessageHandle(), getId(), getName(), parameterData);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        JSONObject msg = new JSONObject();
        msg.put(MESSAGE_HANDLE, getMessageHandle());
        msg.put(MESSAGE_NAME, getName());
        msg.put(MESSAGE_ID, getId());
        JSONArray params = new JSONArray();
        msg.put(MESSAGE_PARAMETER_DATA, params);
        for (Object param : getAll()) {
            params.put(param);
        }
        return msg.toString();
    }

    public static SerializableMessage fromString(String s) {
        try {
            JSONObject msg = new JSONObject(s);
            UniqueId messageHandle = UniqueId.fromString(msg.getString(MESSAGE_HANDLE));
            int id = msg.getInt(MESSAGE_ID);
            String name = msg.getString(MESSAGE_NAME);
            List<Object> params = msg.getJSONArray(MESSAGE_PARAMETER_DATA).toList();
            return new SerializableMessage(messageHandle, id, name, params.toArray());
        } catch (JSONException e) {
            throw new DeserializationException("Could not parse JSON 'Message' instance", e);
        }
    }

}
