.if ( is_many )
    public void add${capital_ref_name}( ${type_name} $l{type_name} ) throws XtumlException {
        if ( !(${ref_name}.contains( $l{type_name} )) ) ${ref_name}.add( $l{type_name} );
        else throw new LinkException( "Cannot link to already linked relationship." );
    }
.else
    public void set${capital_ref_name}( ${type_name} $l{type_name} ) throws XtumlException {
        if ( ${ref_name} instanceof IEmptyInstance ) ${ref_name} = $l{type_name};
        else throw new LinkException( "Cannot link to already linked relationship." );
    }
.end if
