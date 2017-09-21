package com.cierasystems.cairn.components;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cierasystems.summit.classes.IInstanceSet;
import com.cierasystems.summit.components.IComponent;

public abstract class Component implements IComponent {
    
    public Component() {
        Map<Class<?>, IInstanceSet> instancePopulation = new ConcurrentHashMap<Class<?>, IInstanceSet>();
        for ( Class<?> type : getClasses() ) {
            instancePopulation.put( type, getNewInstanceSet( type ) );
        }
        this.setInstancePopulation( instancePopulation );
    }
}
