T_b("    ");
T_b("public ");
T_b(type_name);
T_b(" ");
T_b(name);
T_b("() throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("return ");
T_b(name);
T_b("( null );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(type_name);
T_b(" ");
T_b(name);
T_b("( IWhere condition ) throws XtumlException {");
T_b("\n");
if ( is_many ) {
T_b("        ");
T_b(type_name);
T_b(" ");
T_b(T_l(type_name));
T_b(" = new ");
T_b(type_name);
T_b("();");
T_b("\n");
}
T_b("        ");
T_b("IRelationshipSet R");
T_b(rel_num);
T_b("set = ((");
T_b(relationship_cast);
T_b("Set)getContext().getRelationshipSet( ");
T_b(rel_num);
T_b(" )).getBy");
T_b(T_c(src_class));
T_b("Id( getInstanceId() );");
T_b("\n");
if ( unconditional ) {
T_b("        ");
T_b("if ( R");
T_b(rel_num);
T_b("set.isEmpty() ) throw new ModelIntegrityException( \"Unconditional association has no related instances.\" );");
T_b("\n");
}
if ( is_many ) {
T_b("        ");
T_b("for ( IRelationship r");
T_b(rel_num);
T_b(" : R");
T_b(rel_num);
T_b("set ) {");
T_b("\n");
T_b("            ");
T_b("IModelInstance candidate = getContext().getInstanceSet( ");
T_b(T_l(type_name));
T_b(".getKeyLetters() ).getByInstanceId( ((");
T_b(relationship_cast);
T_b(")r");
T_b(rel_num);
T_b(").get");
T_b(T_c(dst_class));
T_b("() );");
T_b("\n");
T_b("            ");
T_b("if ( null == condition || condition.passes( candidate ) ) ");
T_b(T_l(type_name));
T_b(".add( candidate );");
T_b("\n");
T_b("        ");
T_b("}");
T_b("\n");
T_b("        ");
T_b("return (");
T_b(type_name);
T_b(")");
T_b(T_l(type_name));
T_b(".toImmutableSet();");
T_b("\n");
} else {
if ( multiplicity_many ) {
T_b("        ");
T_b("for ( IRelationship r");
T_b(rel_num);
T_b(" : R");
T_b(rel_num);
T_b("set ) {");
T_b("\n");
T_b("            ");
T_b("IModelInstance candidate = getContext().getInstanceSet( ");
T_b(type_name);
T_b(".KEY_LETTERS ).getByInstanceId( ((");
T_b(relationship_cast);
T_b(")r");
T_b(rel_num);
T_b(").get");
T_b(T_c(dst_class));
T_b("() );");
T_b("\n");
} else {
T_b("        ");
T_b("if ( 1 == R");
T_b(rel_num);
T_b("set.size() ) {");
T_b("\n");
T_b("            ");
T_b("IModelInstance candidate = getContext().getInstanceSet( ");
T_b(type_name);
T_b(".KEY_LETTERS ).getByInstanceId( ((");
T_b(relationship_cast);
T_b(")R");
T_b(rel_num);
T_b("set.iterator().next()).get");
T_b(T_c(dst_class));
T_b("() );");
T_b("\n");
}
T_b("            ");
T_b("if ( ");
if ( 0==strcmp("subtype",dst_class) ) {
T_b("candidate instanceof ");
T_b(type_name);
T_b(" && ( ");
}
T_b("null == condition || condition.passes( candidate )");
if ( 0==strcmp("subtype",dst_class) ) {
T_b(" ");
T_b(")");
}
T_b(" ");
T_b(") return (");
T_b(type_name);
T_b(")candidate;");
T_b("\n");
if ( multiplicity_many ) {
T_b("        ");
T_b("}");
T_b("\n");
T_b("        ");
T_b("return ");
T_b(type_name);
T_b(".EMPTY_");
T_b(T_underscore(T_u(type_name)));
T_b(";");
T_b("\n");
} else {
T_b("            ");
T_b("else return ");
T_b(type_name);
T_b(".EMPTY_");
T_b(T_underscore(T_u(type_name)));
T_b(";");
T_b("\n");
T_b("        ");
T_b("}");
T_b("\n");
T_b("        ");
T_b("else throw new ModelIntegrityException( \"Association with multiplicity 'one' has more than one related instance.\" );");
T_b("\n");
}
}
T_b("    ");
T_b("}");
T_b("\n");
