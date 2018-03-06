if ( 0 == relationship_type ) {
T_b("        ");
T_b("relationships.put( ");
T_b(T_s(rel_num));
T_b(", new BinaryRelationshipSet( ");
T_b(T_s(rel_num));
T_b(" ) );");
T_b("\n");
} else if ( 1 == relationship_type ) {
T_b("        ");
T_b("relationships.put( ");
T_b(T_s(rel_num));
T_b(", new AssociativeRelationshipSet( ");
T_b(T_s(rel_num));
T_b(" ) );");
T_b("\n");
} else if ( 2 == relationship_type ) {
T_b("        ");
T_b("relationships.put( ");
T_b(T_s(rel_num));
T_b(", new SubsuperRelationshipSet( ");
T_b(T_s(rel_num));
T_b(" ) );");
T_b("\n");
}
