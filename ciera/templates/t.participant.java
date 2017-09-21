.if ( is_many )
    public void add${capital_ref_name}( ${target_type_name} ${inst_name} ) throws XtumlException {
        if ( !(${ref_name}.contains( ${inst_name} )) ) ${ref_name}.add( ${inst_name} );
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void remove${capital_ref_name}( ${target_type_name} ${inst_name} ) throws XtumlException {
        if ( ${ref_name}.contains( ${inst_name} ) ) ${ref_name}.remove( ${inst_name} );
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
.else
    public void set${capital_ref_name}( ${target_type_name} ${inst_name} ) throws XtumlException {
        if ( ${ref_name} instanceof IEmptyInstance ) ${ref_name} = ${inst_name};
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clear${capital_ref_name}() throws XtumlException {
        if ( !(${ref_name} instanceof IEmptyInstance) ) ${ref_name} = ${target_type_name}.empty${target_type_name};
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
.end if
