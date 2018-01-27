.// binary relationships
.if ( 0 == relationship_type )
        relationships.put( ${rel_num}, new BinaryRelationshipSet( ${rel_num} ) );
.// associative relationships
.elif ( 1 == relationship_type )
        relationships.put( ${rel_num}, new AssociativeRelationshipSet( ${rel_num} ) );
.// subsuper relationships
.elif ( 2 == relationship_type )
        relationships.put( ${rel_num}, new SubsuperRelationshipSet( ${rel_num} ) );
.end if
