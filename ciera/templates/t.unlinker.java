.if ( is_many )
    public void remove${capital_ref_name}( ${type_name} $l{type_name} ) throws XtumlException {
        if ( ${ref_name}.contains( $l{type_name} ) ) ${ref_name}.remove( $l{type_name} );
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
.else

    public void clear${capital_ref_name}() throws XtumlException {
        if ( !(${ref_name} instanceof IEmptyInstance) ) ${ref_name} = ${type_name}.empty${type_name};
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
.end if
