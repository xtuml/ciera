if ( is_getter ) {
} else {
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public void ");
T_b(name);
T_b("( ");
T_b(type_name);
T_b(" ");
T_b(self->attribute_name);
T_b(" ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("for ( ");
T_b(self->class_name);
T_b(" ");
T_b(T_l(self->class_name));
T_b(" : this ) ");
T_b(T_l(self->class_name));
T_b(".");
T_b(name);
T_b("( ");
T_b(self->attribute_name);
T_b(" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
}
