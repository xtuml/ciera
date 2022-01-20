package io.ciera.runtime.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.json.JSONException;
import org.json.JSONObject;

import io.ciera.runtime.api.domain.Message;
import io.ciera.runtime.api.exceptions.DeserializationException;
import io.ciera.runtime.api.types.UniqueId;

/**
 * The SerializableMessage class extends {@link PortMessage} with the ability to
 * dump and parse the message to a JSON string for the purpose of passing the
 * message across some sort of interface between deployed processes.
 */
public class JSONMessage extends PortMessage {

    public static final String MESSAGE_HANDLE = "messageHandle";
    public static final String MESSAGE_NAME = "name";
    public static final String MESSAGE_ID = "id";
    public static final String MESSAGE_PARAMETER_DATA = "parameterData";

    private final boolean parametersAreValid;

    public JSONMessage(Message message) {
        this(message.getMessageHandle(), message.getId(), message.getName(), message.getParameterData(),
                message instanceof JSONMessage ? ((JSONMessage) message).parametersAreValid : true);
    }

    public JSONMessage(UniqueId messageHandle, int id, String name, Map<String, Object> parameterData,
            boolean parametersAreValid) {
        super(messageHandle, id, name, parameterData);
        this.parametersAreValid = parametersAreValid;
    }

    @Override
    public Object get(String key) {
        if (parametersAreValid) {
            return super.get(key);
        } else {
            throw new DeserializationException("Cannot access data on a partially deserialized message: " + this);
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

    public static JSONMessage fromString(String s) {
        return fromString(s, null);
    }

    public static JSONMessage fromString(String s, Class<?> iface) {
        try {
            JSONObject msg = new JSONObject(s);
            UniqueId messageHandle = UniqueId.fromString(msg.getString(MESSAGE_HANDLE));
            int id = msg.getInt(MESSAGE_ID);
            String name = msg.getString(MESSAGE_NAME);
            Map<String, Object> params = msg.getJSONObject(MESSAGE_PARAMETER_DATA).toMap();
            // if an interface class is passed, attempt to use it to deserialize the
            // parameter data
            if (iface != null) {
                try {
                    return new JSONMessage(messageHandle, id, name,
                            deserializeParameters(params, getTypeMap(iface).get(id)), true);
                } catch (DeserializationException e) {
                    e.printStackTrace();
                    // failed to automatically deserialize; do nothing
                }
            }
            return new JSONMessage(messageHandle, id, name, params, false);
        } catch (JSONException e) {
            throw new DeserializationException("Could not parse JSON 'Message' instance: " + s, e);
        }
    }

    public static Map<String, Object> deserializeParameters(Map<String, Object> parameterData,
            Map<String, Class<?>> parameterTypes) {
        Map<String, Object> newParameterData = new TreeMap<>();
        if (parameterData != null && parameterTypes != null && parameterData.keySet().equals(parameterTypes.keySet())) {
            for (String key : parameterData.keySet()) {
                Class<?> type = parameterTypes.get(key);
                try {
                    if (int.class.isAssignableFrom(type)) {
                        newParameterData.put(key, ((Number) parameterData.get(key)).intValue());
                    } else if (long.class.isAssignableFrom(type)) {
                        newParameterData.put(key, ((Number) parameterData.get(key)).longValue());
                    } else if (double.class.isAssignableFrom(type)) {
                        newParameterData.put(key, ((Number) parameterData.get(key)).doubleValue());
                    } else if (boolean.class.isAssignableFrom(type)) {
                        newParameterData.put(key, (boolean) parameterData.get(key));
                    } else if (Enum.class.isAssignableFrom(type)) {
                        Method loader = type.getMethod("valueOf", Class.class, String.class);
                        newParameterData.put(key, loader.invoke(null, type, parameterData.get(key)));
                    } else {
                        Method loader = type.getMethod("fromString", String.class);
                        newParameterData.put(key, loader.invoke(null, parameterData.get(key)));
                    }
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    throw new DeserializationException(e);
                }
            }
            return Collections.unmodifiableMap(newParameterData);
        } else {
            throw new DeserializationException(new IllegalArgumentException());
        }
    }

    // build a map of all the message parameter types from annotations in the
    // interface
    private static Map<Integer, Map<String, Class<?>>> getTypeMap(Class<?> cls) {
        Map<Integer, Map<String, Class<?>>> typeMap = new TreeMap<>();
        Stream.of(cls.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()) && int.class.equals(f.getType())
                        && f.getAnnotation(PortMessage.Names.class) != null
                        && f.getAnnotation(PortMessage.Types.class) != null)
                .forEach(f -> {
                    Map<String, Class<?>> msgTypes = new TreeMap<>();
                    try {
                        typeMap.put(f.getInt(null), msgTypes);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new DeserializationException(e);
                    }
                    String[] names = f.getAnnotation(PortMessage.Names.class).names();
                    Class<?>[] types = f.getAnnotation(PortMessage.Types.class).types();
                    if (names.length == types.length) {
                        for (int i = 0; i < names.length; i++) {
                            msgTypes.put(names[i], types[i]);
                        }
                    }
                });
        return typeMap;
    }

}
