package io.ciera.runtime.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.ciera.runtime.api.domain.Message;
import io.ciera.runtime.api.types.UniqueId;

/**
 * A message represents an asynchronous event sent across domain boundaries. A message has a unique
 * identifier to mark which message it is as well as data items. Messages can be serialized and sent
 * across a network interface.
 */
public class PortMessage implements Message {

  private static final long serialVersionUID = 1L;

  public static final int NULL_SIGNAL = 0;

  private final UniqueId messageHandle;
  private final int messageId;
  private final String name;
  private final Map<String, Object> parameterData;

  public PortMessage() {
    this(new UniqueId(), NULL_SIGNAL, null, Map.<String, Object>of());
  }

  public PortMessage(final int id) {
    this(id, Map.<String, Object>of());
  }

  public PortMessage(final int id, final Map<String, Object> parameterData) {
    this(UniqueId.random(), id, null, new HashMap<>(parameterData));
  }

  public PortMessage(
      final UniqueId messageHandle,
      final int id,
      final String name,
      final Map<String, Object> parameterData) {
    this.messageHandle = messageHandle;
    messageId = id;
    this.name = name;
    this.parameterData = parameterData;
  }

  /** {@inheritDoc} */
  @Override
  public Object get(final String key) {
    return Optional.of(parameterData.get(key)).orElseThrow();
  }

  @Override
  public void put(final String key, final Object value) {
    parameterData.put(key, value);
  }

  @Override
  public Map<String, Object> getParameterData() {
    return parameterData;
  }

  /**
   * Get the unique ID of this message instance.
   *
   * @return the message's unique ID.
   */
  @Override
  public UniqueId getMessageHandle() {
    return messageHandle;
  }

  /**
   * Get the informal name of this message instance.
   *
   * @return The name of the message.
   */
  @Override
  public String getName() {
    return name != null ? name : getClass().getSimpleName();
  }

  /**
   * Get the message number for this message. This number uniquely identifies an abstract message in
   * an interface.
   */
  @Override
  public int getId() {
    return messageId;
  }

  @Override
  public int compareTo(final Message m) {
    return messageHandle.compareTo(m.getMessageHandle());
  }

  @Override
  public boolean equals(final Object o) {
    return o instanceof Message && messageHandle.equals(((Message) o).getMessageHandle());
  }

  @Override
  public int hashCode() {
    return messageHandle.hashCode();
  }

  public static Message fromString(final Object s) {
    throw new UnsupportedOperationException("Base 'Message' is not JSON serializable");
  }

  @Override
  public String toString() {
    return String.format("%s[%.8s]", getName(), messageHandle);
  }
}