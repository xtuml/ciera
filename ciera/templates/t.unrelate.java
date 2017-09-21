.if ( is_formalizer )
  .if ( is_empty )
    @Override
    public synchronized void unrelateFrom${target_key_letters}AcrossR${rel_numb}${phrase}( ${target_type_name} ${target_inst_name} ) throws XtumlException {
        throw new EmptyInstanceException( "Unrelate of empty instance" );
    }
  .else
    public synchronized void unrelateFrom${target_key_letters}AcrossR${rel_numb}${phrase}( ${target_type_name} ${target_inst_name} ) throws XtumlException {
        checkLiving();
        ${target_inst_name}.checkLiving();
    .if ( is_many )
        if ( ${ref_name}.contains( ${target_inst_name} ) ) {
            ${ref_name}.remove( ${target_inst_name} );
      .if ( corresponding_is_many )
            ${target_inst_name}.remove${capital_ref_name}( this );
      .else
            ${target_inst_name}.clear${capital_ref_name}();
      .end if
            // TODO set referential attributes
        }
    .else
        if ( !(${ref_name} instanceof IEmptyInstance) ) {
            ${ref_name} = ${target_type_name}.empty${target_type_name};
      .if ( corresponding_is_many )
            ${target_inst_name}.remove${capital_ref_name}( this );
      .else
            ${target_inst_name}.clear${capital_ref_name}();
      .end if
            // TODO set referential attributes
        }
    .end if
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
  .end if
.else
  .if ( is_empty )
    @Override
    public void unrelateFrom${target_key_letters}AcrossR${rel_numb}${phrase}( ${target_type_name} ${target_inst_name} ) throws XtumlException {
        throw new EmptyInstanceException( "Unrelate of empty instance" );
    }
  .else
    public void unrelateFrom${target_key_letters}AcrossR${rel_numb}${phrase}( ${target_type_name} ${target_inst_name} ) throws XtumlException {
        ${target_inst_name}.unrelateFrom${class_kl}AcrossR${rel_numb}${corresponding_phrase}( this );
    }
  .end if
.end if
