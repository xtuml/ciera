package io.ciera.runtime;

public interface IVersioned {

    default public String getVersion() {
        return "Unknown";
    }

    default public String getVersionDate() {
        return "Unknown";
    }

}
