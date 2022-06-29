package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.types.Device;

public class DeviceReadException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Device device;

  public DeviceReadException(final String message, final Throwable cause, final Device device) {
    super(message, cause);
    this.device = device;
  }

  public Device getDevice() {
    return device;
  }

  @Override
  public String getMessage() {
    return super.getMessage() + ": [device=" + device + "]";
  }
}
