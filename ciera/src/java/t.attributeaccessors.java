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
T_b("// TODO check referentials match");
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
if ( is_referential_attr ) {
if ( is_primitive_type ) {
T_b("        ");
T_b("if ( ");
T_b(attr_name);
T_b(" != get");
T_b(attr_camel_case_name);
T_b("() ) {");
T_b("\n");
} else {
T_b("        ");
T_b("if ( !");
T_b(attr_name);
T_b(".equals( get");
T_b(attr_camel_case_name);
T_b("() ) ) {");
T_b("\n");
}
T_b("            ");
T_b("this.");
T_b(attr_name);
T_b(" = ");
T_b(attr_name);
T_b(";");
T_b("\n");
T_b("            ");
T_b("// TODO propagate referentials");
T_b("\n");
T_b("        ");
T_b("}");
T_b("\n");
} else {
T_b("        ");
T_b("this.");
T_b(attr_name);
T_b(" = ");
T_b(attr_name);
T_b(";");
T_b("\n");
}
T_b("    ");
T_b("}");
T_b("\n");
