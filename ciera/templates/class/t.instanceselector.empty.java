.if ( returns_set )
    @Override
    public ${type_name} ${self.name}( IWhere condition ) {
        return (${type_name})(new ${type_name}()).toImmutableSet();
    }
.else
    @Override
    public ${type_name} ${self.name}( IWhere condition ) {
        return ${type_name}.EMPTY_$u_{type_name};
    }
.end if
