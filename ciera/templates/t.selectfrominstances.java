.if ( isgeneric )
    public synchronized ${class_name} selectAny${class_key_letters}FromInstances() {
        return ((${class_set_name})getInstanceSet(${class_name}.class)).selectAny${class_key_letters}FromInstances( null );
    }

    public synchronized ${class_name} selectAny${class_key_letters}FromInstances( IWhere condition ) {
        return ((${class_set_name})getInstanceSet(${class_name}.class)).selectAny${class_key_letters}FromInstances( condition );
    }

    public synchronized ${class_set_name} selectMany${class_key_letters}sFromInstances() {
        return (${class_set_name})getInstanceSet(${class_name}.class);
    }

    public synchronized ${class_set_name} selectMany${class_key_letters}sFromInstances( IWhere condition ) {
        return ((${class_set_name})getInstanceSet(${class_name}.class)).selectMany${class_key_letters}sFromInstances( condition );
    }
.else
  .if ( not isempty )
    public synchronized ${class_name} selectAny${class_key_letters}FromInstances( IWhere condition ) {
        return (${class_name})selectAny( condition );
    }
    
    public synchronized ${class_set_name} selectMany${class_key_letters}sFromInstances( IWhere condition ) {
        return (${class_set_name})selectMany( condition );
    }
  .end if
.end if
