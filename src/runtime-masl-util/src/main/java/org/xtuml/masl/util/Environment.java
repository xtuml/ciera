package org.xtuml.masl.util;

public class Environment {

    public Environment(final Object domain) {
        System.loadLibrary("Environment_interface");
    }

    public native void setenv(final String name, final String value);

    public native void unsetenv(final String name);

    public native String getenv(final String name);

    public native boolean isset(final String name);

}