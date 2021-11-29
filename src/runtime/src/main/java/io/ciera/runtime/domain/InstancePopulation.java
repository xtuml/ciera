package io.ciera.runtime.domain;

import java.util.Set;
import java.util.function.Predicate;

import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.Named;
import io.ciera.runtime.application.Timer;
import io.ciera.runtime.types.UniqueId;

public interface InstancePopulation extends Named {

    public <T extends ObjectInstance> T createInstance(Class<T> object);

    public void addInstance(ObjectInstance instance);

    public <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where);

    public <T extends ObjectInstance> T getInstance(Class<T> object, UniqueId instanceId);

    public <T extends ObjectInstance> T getInstance(Class<T> object);

    public <T extends ObjectInstance> Set<T> getAllInstances(Class<T> object);

    public void deleteInstance(ObjectInstance instance);

    public void addEvent(Event event);

    public Event getEvent(UniqueId eventHandle);

    public void removeEvent(Event event);

    public void addTimer(Timer timer);

    public Timer getTimer(UniqueId timerHandle);

    public void removeTimer(Timer timer);

    public void addMessage(Message message);

    public Message getMessage(UniqueId messageHandle);

    public void removeMessage(Message message);

}