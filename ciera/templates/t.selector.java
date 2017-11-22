.if ( is_many )
    public ${target_type_obj_name} selectAny${type_key_letters}OnR${rel_numb}${phrase}() throws XtumlException {
        return selectAny${type_key_letters}OnR${rel_numb}${phrase}( null );
    }

    public ${target_type_obj_name} selectAny${type_key_letters}OnR${rel_numb}${phrase}( IWhere condition ) throws XtumlException {
        checkLiving();
  .if ( is_unconditional )
        if ( ${ref_name}.isEmpty() ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
        else return ${ref_name}.selectAny${type_key_letters}FromInstances( condition );
  .else
        return ${ref_name}.selectAny${type_key_letters}FromInstances( condition );
  .end if
    }

    public ${type_name} selectMany${type_key_letters}sOnR${rel_numb}${phrase}() throws XtumlException {
        return selectMany${type_key_letters}sOnR${rel_numb}${phrase}( null );
    }

    public ${type_name} selectMany${type_key_letters}sOnR${rel_numb}${phrase}( IWhere condition ) throws XtumlException {
        checkLiving();
  .if ( is_unconditional )
        if ( ${ref_name}.isEmpty() ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
        else return ${ref_name}.selectMany${type_key_letters}sFromInstances( condition );
  .else
        return ${ref_name}.selectMany${type_key_letters}sFromInstances( condition );
  .end if
    }
.else
    public ${type_name} selectOne${type_key_letters}OnR${rel_numb}${phrase}() throws XtumlException {
        return selectOne${type_key_letters}OnR${rel_numb}${phrase}( null );
    }
    
    public ${type_name} selectOne${type_key_letters}OnR${rel_numb}${phrase}( IWhere condition ) throws XtumlException {
        checkLiving();
  .if ( is_unconditional )
        if ( ${ref_name} instanceof IEmptyInstance ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
    .if ( is_subtype )
        else if ( ${ref_name} instanceof ${type_name} && ( null == condition || condition.evaluate( ${ref_name} ) ) ) return (${type_name})${ref_name};
    .else
        else if ( null == condition || condition.evaluate( ${ref_name} ) ) return ${ref_name};
    .end if
  .else
        if ( null == condition || condition.evaluate( ${ref_name} ) ) return ${ref_name};
  .end if
        else return ${type_name}.empty${type_name};
    }
.end if
