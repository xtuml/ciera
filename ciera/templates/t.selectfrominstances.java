.if ( isgeneric )
    public ${class_name} selectAny${class_key_letters}FromInstances() {
        return ((${class_set_name})getInstanceSet(${class_name}.class)).selectAny${class_key_letters}FromInstances( null );
    }

    public ${class_name} selectAny${class_key_letters}FromInstances( Where condition ) {
        return ((${class_set_name})getInstanceSet(${class_name}.class)).selectAny${class_key_letters}FromInstances( condition );
    }

    public ${class_set_name} selectMany${class_key_letters}sFromInstances() {
        return (${class_set_name})getInstanceSet(${class_name}.class);
    }

    public ${class_set_name} selectMany${class_key_letters}sFromInstances( Where condition ) {
        return ((${class_set_name})getInstanceSet(${class_name}.class)).selectMany${class_key_letters}sFromInstances( condition );
    }
.else
  .if ( isempty )
    @Override
    public ${class_name} selectAny${class_key_letters}FromInstances( Where condition ) {
        return ${class_name}.empty${class_name};
    }
    
    @Override
    public ${class_set_name} selectMany${class_key_letters}sFromInstances( Where condition ) {
        return ${class_set_name}.empty${class_set_name};
    }
  .else
    public ${class_name} selectAny${class_key_letters}FromInstances( Where condition ) {
        return (${class_name})selectAny( condition );
    }
    
    public ${class_set_name} selectMany${class_key_letters}sFromInstances( Where condition ) {
        return (${class_set_name})selectMany( condition );
    }
  .end if
.end if
