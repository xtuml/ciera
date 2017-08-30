.if ( is_formalizer )
    public synchronized void relateTo${target_key_letters}AcrossR${rel_numb}${phrase}( ${target_type_name} ${target_inst_name} ) throws XtumlException {
        checkLiving();
        ${target_inst_name}.checkLiving();
  .if ( is_many )
        if ( !(${ref_name}.contains( ${target_inst_name} )) ) {
            ${ref_name}.add( ${target_inst_name} );
    .if ( corresponding_is_many )
            ${target_inst_name}.add${capital_ref_name}( this );
    .else
            ${target_inst_name}.set${capital_ref_name}( this );
    .end if
            // TODO set referential attributes
        }
  .else
        if ( ${ref_name} instanceof EmptyInstance ) {
            ${ref_name} = ${target_inst_name};
    .if ( corresponding_is_many )
            ${target_inst_name}.add${capital_ref_name}( this );
    .else
            ${target_inst_name}.set${capital_ref_name}( this );
    .end if
            // TODO set referential attributes
        }
  .end if
        else throw new LinkException( "Cannot link to already linked relationship." );
    }
.else
    public void relateTo${target_key_letters}AcrossR${rel_numb}${phrase}( ${target_type_name} ${target_inst_name} ) throws XtumlException {
        ${target_inst_name}.relateTo${class_kl}AcrossR${rel_numb}${corresponding_phrase}( this );
    }
.end if
