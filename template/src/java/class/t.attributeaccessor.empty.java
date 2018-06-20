if ( is_getter ) {
T_b("    ");
T_b("public ");
T_b(type_name);
T_b(" ");
T_b(name);
T_b("() throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("throw new EmptyInstanceException( \"Cannot get attribute of empty instance.\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
} else {
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
T_b("throw new EmptyInstanceException( \"Cannot set attribute of empty instance.\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
}
