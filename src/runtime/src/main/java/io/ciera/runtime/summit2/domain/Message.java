package io.ciera.runtime.summit2.domain;

import io.ciera.runtime.summit2.exceptions.DeserializationException;
import io.ciera.runtime.summit2.types.MessageHandle;
import io.ciera.runtime.summit2.types.UniqueId;

/**
 * A message represents an asynchronous event sent across domain boundaries. A
 * message has a unique identifier to mark which message it is as well as data
 * items. Messages can be serialized and sent across a network interface.
 */
public class Message implements Comparable<Message> {

    private MessageHandle messageHandle;
    private String name;
    private int messageId;
    private Object[] parameterData;

    public Message() {
        this(new MessageHandle(), null, 0, new Object[0]);
    }

    public Message(MessageHandle messageHandle, String name, int id, Object... data) {
        this.messageHandle = messageHandle;
        this.name = name;
        this.messageId = id;
        this.parameterData = data;
    }

    /**
     * Access the parameter datum at the specified index.
     * 
     * @param index The index at which to access the datum.
     * @return The parameter datum.
     */
    public Object get(int index) {
        if (index >= 0 && index < parameterData.length) {
            return parameterData[index];
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    /**
     * Get all data items as an array.
     * 
     * @return The array of paramter data.
     */
    public Object[] getAll() {
        return parameterData;
    }

    /**
     * Get the unique ID of this message instance.
     * 
     * @return the message's unique ID.
     */
    public UniqueId getMessageHandle() {
        return messageHandle;
    }

    /**
     * Get the informal name of this message instance.
     * 
     * @return The name of the message.
     */
    public String getName() {
        return name != null ? name : getClass().getSimpleName();
    }

    /**
     * Get the message number for this message. This number uniquely identifies an
     * abstract message in an interface.
     */
    public int getId() {
        return messageId;
    }

    @Override
    public int compareTo(Message m) {
        return messageHandle.compareTo(m.messageHandle);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Message && messageHandle.equals(((Message) o).messageHandle);
    }

    @Override
    public int hashCode() {
        return messageHandle.hashCode();
    }

    public static Message fromString(Object s) {
        throw new DeserializationException("Base 'Message' is not JSON serializable.");
    }

}
