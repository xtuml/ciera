    public void relateTo${type_key_letters}AcrossR${rel_numb}${phrase}( ${type_name} $l{type_name} ) throws XtumlException {
        checkLiving();
        $l{type_name}.checkLiving();
.if ( is_many )
        if ( !(${ref_name}.contains( $l{type_name} )) ) {
            ${ref_name}.add( $l{type_name} );
  .if ( linker_is_many )
            $l{type_name}.add${linker_capital_ref_name}( this );
  .else
            $l{type_name}.set${linker_capital_ref_name}( this );
  .end if
            // TODO set referential attributes
        }
.else
        if ( ${ref_name} instanceof IEmptyInstance ) {
            ${ref_name} = $l{type_name};
  .if ( linker_is_many )
            $l{type_name}.add${linker_capital_ref_name}( this );
  .else
            $l{type_name}.set${linker_capital_ref_name}( this );
  .end if
            // TODO set referential attributes
        }
.end if
        else throw new LinkException( "Cannot link to already linked relationship." );
    }
