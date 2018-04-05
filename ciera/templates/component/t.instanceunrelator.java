        ${test} ( $t{rel_num} == relNum && ${link_parameter_type_check} ) {
            if ( !removeRelationship( ((${self.relationship_set_cast})getRelationshipSet( $t{rel_num} )).getByInstanceIds( ${link_parameter_ids} ) ) ) throw new ModelIntegrityException( "Instances are not related." );
            // TODO propagate referentials?
        }
