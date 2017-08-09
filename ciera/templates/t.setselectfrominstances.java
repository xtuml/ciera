    public ${class_name} selectAny${class_key_letters}FromInstances( Where condition ) {
        return (${class_name})selectAny( condition );
    }
    
    public ${class_set_name} selectMany${class_key_letters}sFromInstances( Where condition ) {
        return (${class_set_name})selectMany( condition );
    }
