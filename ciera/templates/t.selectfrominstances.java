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
