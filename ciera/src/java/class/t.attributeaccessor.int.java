if ( is_getter ) {
T_b("    ");
T_b("public ");
T_b(type_name);
T_b(" get");
T_b(capital_name);
T_b("() throws XtumlException;");
T_b("\n");
} else {
T_b("    ");
T_b("public void set");
T_b(capital_name);
T_b("( ");
T_b(type_name);
T_b(" ");
T_b(self->attribute_name);
T_b(" ) throws XtumlException;");
T_b("\n");
}
