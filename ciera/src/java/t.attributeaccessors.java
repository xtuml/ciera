T_b("    ");
T_b("public ");
T_b(type_name);
T_b(" get");
T_b(capital_name);
T_b("() throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("checkLiving();");
T_b("\n");
if ( is_referential ) {
T_b("        ");
T_b("// TODO check referentials match");
T_b("\n");
}
T_b("        ");
T_b("return ");
T_b(name);
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("public void set");
T_b(capital_name);
T_b("( ");
T_b(type_name);
T_b(" ");
T_b(name);
T_b(" ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("checkLiving();");
T_b("\n");
if ( is_referential ) {
if ( is_primitive_type ) {
T_b("        ");
T_b("if ( ");
T_b(name);
T_b(" != get");
T_b(capital_name);
T_b("() ) {");
T_b("\n");
} else {
T_b("        ");
T_b("if ( !");
T_b(name);
T_b(".equals( get");
T_b(capital_name);
T_b("() ) ) {");
T_b("\n");
}
T_b("            ");
T_b("this.");
T_b(name);
T_b(" = ");
T_b(name);
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
T_b(name);
T_b(" = ");
T_b(name);
T_b(";");
T_b("\n");
}
T_b("    ");
T_b("}");
T_b("\n");
