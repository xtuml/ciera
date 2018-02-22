    public void ${name}( ${link_parameter_list} ) throws XtumlException {
        if ( ${link_parameter_null_check} ) throw new BadArgumentException( "Null instances passed." );
        if ( ${link_parameter_empty_check} ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        // TODO check cardinality
        if ( !addRelationship( new ${relationship_name}( ${link_parameter_ids} ) ) ) throw new ModelIntegrityException( "Instances already related." );
        // TODO propagate referentials?
    }
