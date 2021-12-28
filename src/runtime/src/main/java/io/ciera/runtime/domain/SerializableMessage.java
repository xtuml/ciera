package io.ciera.runtime.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
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
        this(message.getMessageHandle(), message.getId(), message.getName(), message.getParameterData());
    }

    public SerializableMessage(UniqueId messageHandle, int id, String name, Map<String, Object> parameterData) {
        super(messageHandle, id, name, parameterData);
    }

    public SerializableMessage deserializeData(Map<String, Class<?>> parameterTypes) {
        Map<String, Object> parameterData = new TreeMap<>();
        if (getParameterData().keySet().equals(parameterTypes.keySet())) {
            for (String key : getParameterData().keySet()) {
                Class<?> type = parameterTypes.get(key);
                try {
                    if (int.class.isAssignableFrom(type)) {
                        parameterData.put(key, ((Number) get(key)).intValue());
                    } else if (long.class.isAssignableFrom(type)) {
                        parameterData.put(key, ((Number) get(key)).longValue());
                    } else if (double.class.isAssignableFrom(type)) {
                        parameterData.put(key, ((Number) get(key)).doubleValue());
                    } else if (boolean.class.isAssignableFrom(type)) {
                        parameterData.put(key, (boolean) get(key));
                    } else if (Enum.class.isAssignableFrom(type)) {
                        Method loader = type.getMethod("valueOf", Class.class, String.class);
                        parameterData.put(key, loader.invoke(null, type, get(key)));
                    } else {
                        Method loader = type.getMethod("fromString", String.class);
                        parameterData.put(key, loader.invoke(null, get(key)));
                    }
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    throw new DeserializationException(e);
                }
            }
            return new SerializableMessage(getMessageHandle(), getId(), getName(), Collections.unmodifiableMap(parameterData));
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
        JSONObject params = new JSONObject(getParameterData());
        msg.put(MESSAGE_PARAMETER_DATA, params);
        return msg.toString();
    }

    public static SerializableMessage fromString(String s) {
        try {
            JSONObject msg = new JSONObject(s);
            UniqueId messageHandle = UniqueId.fromString(msg.getString(MESSAGE_HANDLE));
            int id = msg.getInt(MESSAGE_ID);
            String name = msg.getString(MESSAGE_NAME);
            Map<String, Object> params = msg.getJSONObject(MESSAGE_PARAMETER_DATA).toMap();
            return new SerializableMessage(messageHandle, id, name, params);
        } catch (JSONException e) {
            throw new DeserializationException("Could not parse JSON 'Message' instance", e);
        }
    }

}