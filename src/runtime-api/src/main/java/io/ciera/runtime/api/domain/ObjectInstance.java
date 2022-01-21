package io.ciera.runtime.api.domain;

import io.ciera.runtime.api.action.InstanceActionHome;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.types.UniqueId;

public interface ObjectInstance extends InstanceActionHome, EventTarget {

    public UniqueId getInstanceId();

    public void delete();

    public boolean isEmpty();

    public boolean isAlive();

}