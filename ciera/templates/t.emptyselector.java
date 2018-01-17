.if ( is_many )
    @Override
    public ${type_name} ${name}( IWhere condition ) {
        return new ${type_name}();
    }
.else
    @Override
    public ${type_name} ${name}( IWhere condition ) {
        return ${type_name}.EMPTY_$u_{type_name};
    }
.end if
