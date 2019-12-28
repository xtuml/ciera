package io.ciera.runtime.summit.application;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.IComponent;

public interface IInstanceActionHome<T extends IModelInstance<T, C>, C extends IComponent<C>> extends IActionHome<C> {

    public T self();

}
