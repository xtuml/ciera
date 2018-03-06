.// binary relationships
.if ( 0 == relationship_type )
        relationships.put( $t{rel_num}, new BinaryRelationshipSet( $t{rel_num} ) );
.// associative relationships
.elif ( 1 == relationship_type )
        relationships.put( $t{rel_num}, new AssociativeRelationshipSet( $t{rel_num} ) );
.// subsuper relationships
.elif ( 2 == relationship_type )
        relationships.put( $t{rel_num}, new SubsuperRelationshipSet( $t{rel_num} ) );
.end if
