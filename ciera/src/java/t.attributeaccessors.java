T_b("    ");
T_b("public synchronized ");
T_b(attr_type_name);
T_b(" get");
T_b(attr_camel_case_name);
T_b("() throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("checkLiving();");
T_b("\n");
if ( is_referential_attr ) {
T_b("        ");
T_b("// TODO: ");
T_b(attr_name);
T_b(" = some selection here");
T_b("\n");
}
T_b("        ");
T_b("return ");
T_b(attr_name);
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
if ( ( ! is_referential_attr ) && ( ! attr_is_unique ) ) {
T_b("    ");
T_b("public synchronized void set");
T_b(attr_camel_case_name);
T_b("( ");
T_b(attr_type_name);
T_b(" ");
T_b(attr_name);
T_b(" ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("checkLiving();");
T_b("\n");
T_b("        ");
T_b("this.");
T_b(attr_name);
T_b(" = ");
T_b(attr_name);
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
}
