    public void set${ref_name}OnR${rel_numb}( ${target_type_name} p_${ref_name} ) throws XtumlException {
        if ( !(${ref_name} instanceof EmptyInstance) ) ${ref_name} = p_${ref_name};
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clear${ref_name}OnR${rel_numb}() throws XtumlException {
        if ( !(${ref_name} instanceof EmptyInstance) ) ${ref_name} = ${target_type_name}.empty${target_type_name};
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
