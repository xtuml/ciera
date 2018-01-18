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
if ( 0 == selection_type ) {
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
T_b("set = ((IBinaryRelationshipSet)getContext().getRelationshipSet( ");
T_b(rel_num);
T_b(" )).getByOneId( getInstanceId() );");
T_b("\n");
if ( unconditional ) {
T_b("        ");
T_b("if ( R");
T_b(rel_num);
T_b("set.isEmpty() ) throw new ModelIntegrityException( \"Unconditional association has no related instances.\" );");
T_b("\n");
}
T_b("        ");
T_b("Iterator<IRelationship> iter = R");
T_b(rel_num);
T_b("set.iterator();");
T_b("\n");
if ( multiplicity_many ) {
T_b("        ");
T_b("while ( iter.hasNext() ) {");
T_b("\n");
if ( is_many ) {
T_b("            ");
T_b("IModelInstance candidate = getContext().getInstanceSet( ");
T_b(T_l(type_name));
T_b(".getKeyLetters() ).getByInstanceId( ((IBinaryRelationship)iter.next()).getOther() );");
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
T_b("            ");
T_b("IModelInstance candidate = getContext().getInstanceSet( ");
T_b(type_name);
T_b(".KEY_LETTERS ).getByInstanceId( ((IBinaryRelationship)iter.next()).getOther() );");
T_b("\n");
T_b("            ");
T_b("if ( null == condition || condition.passes( candidate ) ) return (");
T_b(type_name);
T_b(")candidate;");
T_b("\n");
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
}
} else {
T_b("        ");
T_b("if ( 1 == R");
T_b(rel_num);
T_b("set.size() ) {");
T_b("\n");
T_b("            ");
T_b("IModelInstance candidate = getContext().getInstanceSet( ");
T_b(type_name);
T_b(".KEY_LETTERS ).getByInstanceId( ((IBinaryRelationship)iter.next()).getOther() );");
T_b("\n");
T_b("            ");
T_b("if ( null == condition || condition.passes( candidate ) ) return (");
T_b(type_name);
T_b(")candidate;");
T_b("\n");
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
} else if ( 1 == selection_type ) {
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
T_b("set = ((IBinaryRelationshipSet)getContext().getRelationshipSet( ");
T_b(rel_num);
T_b(" )).getByOtherId( getInstanceId() );");
T_b("\n");
if ( unconditional ) {
T_b("        ");
T_b("if ( R");
T_b(rel_num);
T_b("set.isEmpty() ) throw new ModelIntegrityException( \"Unconditional association has no related instances.\" );");
T_b("\n");
}
T_b("        ");
T_b("Iterator<IRelationship> iter = R");
T_b(rel_num);
T_b("set.iterator();");
T_b("\n");
if ( multiplicity_many ) {
T_b("        ");
T_b("while ( iter.hasNext() ) {");
T_b("\n");
if ( is_many ) {
T_b("            ");
T_b("IModelInstance candidate = getContext().getInstanceSet( ");
T_b(T_l(type_name));
T_b(".getKeyLetters() ).getByInstanceId( ((IBinaryRelationship)iter.next()).getOne() );");
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
T_b("            ");
T_b("IModelInstance candidate = getContext().getInstanceSet( ");
T_b(type_name);
T_b(".KEY_LETTERS ).getByInstanceId( ((IBinaryRelationship)iter.next()).getOne() );");
T_b("\n");
T_b("            ");
T_b("if ( null == condition || condition.passes( candidate ) ) return (");
T_b(type_name);
T_b(")candidate;");
T_b("\n");
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
}
} else {
T_b("        ");
T_b("if ( 1 == R");
T_b(rel_num);
T_b("set.size() ) {");
T_b("\n");
T_b("            ");
T_b("IModelInstance candidate = getContext().getInstanceSet( ");
T_b(type_name);
T_b(".KEY_LETTERS ).getByInstanceId( ((IBinaryRelationship)iter.next()).getOne() );");
T_b("\n");
T_b("            ");
T_b("if ( null == condition || condition.passes( candidate ) ) return (");
T_b(type_name);
T_b(")candidate;");
T_b("\n");
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
} else if ( 2 == selection_type ) {
T_b("        ");
T_b("return null;");
T_b("\n");
} else if ( 3 == selection_type ) {
T_b("        ");
T_b("return null;");
T_b("\n");
} else if ( 4 == selection_type ) {
T_b("        ");
T_b("return null;");
T_b("\n");
} else if ( 5 == selection_type ) {
T_b("        ");
T_b("return null;");
T_b("\n");
} else if ( 6 == selection_type ) {
T_b("        ");
T_b("return null;");
T_b("\n");
} else if ( 7 == selection_type ) {
T_b("        ");
T_b("return null;");
T_b("\n");
} else if ( 8 == selection_type ) {
T_b("        ");
T_b("return null;");
T_b("\n");
} else if ( 9 == selection_type ) {
T_b("        ");
T_b("return null;");
T_b("\n");
} else {
T_b("        ");
T_b("// Code generation error");
T_b("\n");
}
T_b("    ");
T_b("}");
T_b("\n");
