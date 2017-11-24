    public void unrelateFrom${type_key_letters}AcrossR${rel_numb}${phrase}( ${type_name} $l{type_name} ) throws XtumlException {
        checkLiving();
        $l{type_name}.checkLiving();
.if ( is_many )
        if ( ${ref_name}.contains( $l{type_name} ) ) {
            ${ref_name}.remove( $l{type_name} );
  .if ( unlinker_is_many )
            $l{type_name}.remove${unlinker_capital_ref_name}( this );
  .else
            $l{type_name}.clear${unlinker_capital_ref_name}();
  .end if
            // TODO set referential attributes
        }
.else
        if ( !(${ref_name} instanceof IEmptyInstance) ) {
            ${ref_name} = ${type_name}.empty${type_name};
  .if ( unlinker_is_many )
            $l{type_name}.remove${unlinker_capital_ref_name}( this );
  .else
            $l{type_name}.clear${unlinker_capital_ref_name}();
  .end if
            // TODO set referential attributes
        }
.end if
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
