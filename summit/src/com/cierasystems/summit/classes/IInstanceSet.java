package com.cierasystems.summit.classes;

import java.util.Set;

public interface IInstanceSet extends Set<IModelInstance> {
    
    public IModelInstance selectAny( IWhere condition );
    public IInstanceSet selectMany( IWhere condition );
    public IModelInstance getEmptyInstance();

}