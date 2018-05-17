.if ( returns_set )
    @Override
    public ${type_name} ${self.name}() {
        return (new ${type_name}()).toImmutableSet();
    }
.else
    @Override
    public ${type_name} ${self.name}() {
        return ${type_name}.EMPTY_$u_{type_name};
    }
.end if
