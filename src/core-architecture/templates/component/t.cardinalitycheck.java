if ( !((${self.relationship_set_cast})getRelationshipSet( $t{rel_num} ) ).getBy$c{self.participant}Id( ${self.link_parameter_name}.getInstanceId() ).isEmpty() ) throw new ModelIntegrityException( "Cannot relate more than one instance across association." );
