.if ( is_many )
    @Override
    public ${type_name} ${name}( IWhere condition ) {
        return (${type_name})(new ${type_name}()).toImmutableSet();
    }
.else
    @Override
    public ${type_name} ${name}( IWhere condition ) {
        return ${type_name}.EMPTY_$u_{type_name};
    }
.end if
