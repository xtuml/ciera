package io.ciera.runtime.api.exceptions;

import io.ciera.runtime.api.types.Device;

public class DeviceReadException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final Device device;

    public DeviceReadException(String message, Throwable cause, Device device) {
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
