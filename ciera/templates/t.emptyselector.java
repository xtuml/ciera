.if ( is_many )
    @Override
    public ${type_class_name} selectAny${type_key_letters}OnR${rel_numb}${phrase}( IWhere condition ) throws XtumlException {
        return ${type_class_name}.empty${type_class_name};
    }

    @Override
    public ${type_name} selectMany${type_key_letters}sOnR${rel_numb}${phrase}( IWhere condition ) throws XtumlException {
        return new ${type_name}();
    }
.else
    @Override
    public ${type_name} selectOne${type_key_letters}OnR${rel_numb}${phrase}( IWhere condition ) throws XtumlException {
        return ${type_name}.empty${type_name};
    }
.end if
