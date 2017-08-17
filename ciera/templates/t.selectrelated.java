.if ( isset )
.else
  .if ( isempty )
    .if ( is_many )
    @Override
    public ${target_type_name} selectAny${target_key_letters}OnR${rel_numb}( Where condition ) throws XtumlException {
        return ${target_type_name}.empty${target_type_name};
    }
    .else
    @Override
    public ${target_type_name} selectOne${target_key_letters}OnR${rel_numb}( Where condition ) throws XtumlException {
        return ${target_type_name}.empty${target_type_name};
    }
    .end if
  .else
    .if ( is_many )
    public ${target_type_name} selectAny${target_key_letters}OnR${rel_numb}() throws XtumlException {
        return selectAny${target_key_letters}OnR${rel_numb}( null );
    }

    public ${target_type_name} selectAny${target_key_letters}OnR${rel_numb}( Where condition ) throws XtumlException {
        checkLiving();
      .if ( is_unconditional )
        if ( ${ref_name} instanceof EmptySet ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
        else return selectAny${target_key_letters}FromInstances( condition );
      .else
        return selectAny${target_key_letters}sFromInstances( condition );
      .end if
    }

    public ${target_type_name} selectMany${target_key_letters}sOnR${rel_numb}() throws XtumlException {
        return selectMany${target_key_letters}sOnR${rel_numb}( null );
    }

    public ${target_type_name} selectMany${target_key_letters}sOnR${rel_numb}( Where condition ) throws XtumlException {
        checkLiving();
      .if ( is_unconditional )
        if ( ${ref_name} instanceof EmptySet ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
        else return selectMany${target_key_letters}sFromInstances( condition );
      .else
        return selectMany${target_key_letters}sFromInstances( condition );
      .end if
    }
    .else
    public ${target_type_name} selectOne${target_key_letters}OnR${rel_numb}() throws XtumlException {
        return selectOne${target_key_letters}OnR${rel_numb}( null );
    }
    
    public ${target_type_name} selectOne${target_key_letters}OnR${rel_numb}( Where condition ) throws XtumlException {
        checkLiving();
      .if ( is_unconditional )
        if ( ${ref_name} instanceof EmptyInstance ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
        else if ( null == condition || condition.evaluate( ${ref_name} ) ) return ${ref_name};
      .else
        if ( null == condition || condition.evaluate( ${ref_name} ) ) return ${ref_name};
      .end if
        else return ${target_type_name}.empty${target_type_name};
    }
    .end if
  .end if
.end if
