    public void ${self.name}( ${link_parameter_list} ) throws XtumlException {
        if ( ${link_parameter_null_check} ) throw new BadArgumentException( "Null instances passed." );
        if ( ${link_parameter_empty_check} ) throw new EmptyInstanceException( "Cannot relate empty instances." );
        if ( !removeRelationship( ((${self.relationship_set_cast})getRelationshipSet( $t{rel_num} )).getByInstanceIds( ${link_parameter_ids} ) ) ) throw new ModelIntegrityException( "Instances are not related." );
        // TODO propagate referentials?
    }
