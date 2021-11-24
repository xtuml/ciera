package io.ciera.runtime.summit2.domain;

import java.util.Set;
import java.util.function.Predicate;

import io.ciera.runtime.summit2.application.Event;
import io.ciera.runtime.summit2.application.Timer;
import io.ciera.runtime.summit2.types.EventHandle;
import io.ciera.runtime.summit2.types.MessageHandle;
import io.ciera.runtime.summit2.types.TimerHandle;
import io.ciera.runtime.summit2.types.UniqueId;

public interface InstancePopulation {

    public String getName();

    public <T extends ObjectInstance> void createInstance(Class<T> object);

    public void addInstance(ObjectInstance instance);

    public <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where);

    public <T extends ObjectInstance> T getInstance(Class<T> object, UniqueId instanceId);

    public <T extends ObjectInstance> T getInstance(Class<T> object);

    public <T extends ObjectInstance> Set<T> getAllInstances(Class<T> object);

    public void deleteInstance(ObjectInstance instance);
    
    public EventHandle addEvent(Event event);
    
    public Event getEvent(EventHandle eventHandle);
    
    public Event removeEvent(EventHandle eventHandle);
    
    public TimerHandle addTimer(Timer timer);
    
    public Timer getTimer(TimerHandle timerHandle);
    
    public Timer removeTimer(TimerHandle timerHandle);
    
    public MessageHandle addMessage(Message message);
    
    public Message getMessage(MessageHandle messageHandle);
    
    public Message removeMessage(MessageHandle messageHandle);

}