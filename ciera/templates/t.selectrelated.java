.if ( isset )
  .if ( not isempty )
    public synchronized ${target_type_name} selectMany${target_key_letters}sOnR${rel_numb}${phrase}() throws XtumlException {
        return selectMany${target_key_letters}sOnR${rel_numb}${phrase}( null );
    }

    public synchronized ${target_type_name} selectMany${target_key_letters}sOnR${rel_numb}${phrase}( Where condition ) throws XtumlException {
        ${target_type_name} return_set = new ${target_type_name}();
        for ( ModelInstance selected : this ) {
    .if ( is_many )
            return_set.addAll( ((${class_name})selected).selectMany${target_key_letters}sOnR${rel_numb}${phrase}( condition ) );
    .else
            return_set.add( ((${class_name})selected).selectOne${target_key_letters}OnR${rel_numb}${phrase}( condition ) );
    .end if
        }
        return return_set;
    }
  .end if
.else
  .if ( isempty )
    .if ( is_many )
    @Override
    public synchronized ${target_type_obj_name} selectAny${target_key_letters}OnR${rel_numb}${phrase}( Where condition ) throws XtumlException {
        return ${target_type_obj_name}.empty${target_type_obj_name};
    }

    @Override
    public synchronized ${target_type_name} selectMany${target_key_letters}sOnR${rel_numb}${phrase}( Where condition ) throws XtumlException {
        return new ${target_type_name}();
    }
    .else
    @Override
    public synchronized ${target_type_name} selectOne${target_key_letters}OnR${rel_numb}${phrase}( Where condition ) throws XtumlException {
        return ${target_type_name}.empty${target_type_name};
    }
    .end if
  .else
    .if ( is_many )
    public synchronized ${target_type_obj_name} selectAny${target_key_letters}OnR${rel_numb}${phrase}() throws XtumlException {
        return selectAny${target_key_letters}OnR${rel_numb}${phrase}( null );
    }

    public synchronized ${target_type_obj_name} selectAny${target_key_letters}OnR${rel_numb}${phrase}( Where condition ) throws XtumlException {
        checkLiving();
      .if ( is_unconditional )
        if ( ${ref_name}.isEmpty() ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
        else return ${ref_name}.selectAny${target_key_letters}FromInstances( condition );
      .else
        return ${ref_name}.selectAny${target_key_letters}FromInstances( condition );
      .end if
    }

    public synchronized ${target_type_name} selectMany${target_key_letters}sOnR${rel_numb}${phrase}() throws XtumlException {
        return selectMany${target_key_letters}sOnR${rel_numb}${phrase}( null );
    }

    public synchronized ${target_type_name} selectMany${target_key_letters}sOnR${rel_numb}${phrase}( Where condition ) throws XtumlException {
        checkLiving();
      .if ( is_unconditional )
        if ( ${ref_name}.isEmpty() ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
        else return ${ref_name}.selectMany${target_key_letters}sFromInstances( condition );
      .else
        return ${ref_name}.selectMany${target_key_letters}sFromInstances( condition );
      .end if
    }
    .else
    public synchronized ${target_type_name} selectOne${target_key_letters}OnR${rel_numb}${phrase}() throws XtumlException {
        return selectOne${target_key_letters}OnR${rel_numb}${phrase}( null );
    }
    
    public synchronized ${target_type_name} selectOne${target_key_letters}OnR${rel_numb}${phrase}( Where condition ) throws XtumlException {
        checkLiving();
      .if ( is_unconditional )
        if ( ${ref_name} instanceof EmptyInstance ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
        .if ( is_subtype )
        else if ( ${ref_name} instanceof ${target_type_name} && ( null == condition || condition.evaluate( ${ref_name} ) ) ) return (${target_type_name})${ref_name};
        .else
        else if ( null == condition || condition.evaluate( ${ref_name} ) ) return ${ref_name};
        .end if
      .else
        if ( null == condition || condition.evaluate( ${ref_name} ) ) return ${ref_name};
      .end if
        else return ${target_type_name}.empty${target_type_name};
    }
    .end if
  .end if
.end if
