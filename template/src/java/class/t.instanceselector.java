T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(type_name);
T_b(" ");
T_b(self->name);
T_b("() throws XtumlException {");
T_b("\n");
if ( multiplicity_many ) {
T_b("        ");
T_b(type_name);
T_b(" ");
T_b(T_l(type_name));
T_b(" = new ");
T_b(type_name);
T_b("Impl();");
T_b("\n");
}
T_b("        ");
T_b("IRelationshipSet R");
T_b(T_s(self->rel_num));
T_b("set = context().");
T_b(selector_name);
T_b("().get");
if ( self->formalizer ) {
T_b("Participating");
} else {
T_b("Formalizing");
}
T_b("( getInstanceId() );");
T_b("\n");
if ( unconditional ) {
T_b("        ");
T_b("// TODO if ( R");
T_b(T_s(self->rel_num));
T_b("set.isEmpty() ) throw new ModelIntegrityException( \"Unconditional association has no related instances.\" );");
T_b("\n");
} else {
if ( ! multiplicity_many ) {
T_b("        ");
T_b("if ( R");
T_b(T_s(self->rel_num));
T_b("set.isEmpty() ) return ");
T_b(type_name);
T_b("Impl.EMPTY_");
T_b(T_underscore(T_u(type_name)));
T_b(";");
T_b("\n");
}
}
if ( multiplicity_many ) {
T_b("        ");
T_b("for ( IRelationship r");
T_b(T_s(self->rel_num));
T_b(" : R");
T_b(T_s(self->rel_num));
T_b("set ) ");
T_b(T_l(type_name));
T_b(".add( context().");
T_b(target_class_name);
T_b("_instances().getByInstanceId( r");
T_b(T_s(self->rel_num));
T_b(".get");
if ( self->formalizer ) {
T_b("Part");
} else {
T_b("Form");
}
T_b("() ) );");
T_b("\n");
T_b("        ");
T_b("return ");
T_b(T_l(type_name));
T_b(".toImmutableSet();");
T_b("\n");
} else {
T_b("        ");
T_b("if ( 1 == R");
T_b(T_s(self->rel_num));
T_b("set.size() ) return context().");
T_b(target_class_name);
T_b("_instances().getByInstanceId( R");
T_b(T_s(self->rel_num));
T_b("set.iterator().next().get");
if ( self->formalizer ) {
T_b("Part");
} else {
T_b("Form");
}
T_b("() );");
T_b("\n");
T_b("        ");
T_b("else throw new ModelIntegrityException( \"Association with multiplicity 'one' has more than one related instance.\" );");
T_b("\n");
}
T_b("    ");
T_b("}");
T_b("\n");
