T_b("    ");
T_b("public void unrelateFrom");
T_b(type_key_letters);
T_b("AcrossR");
T_b(rel_numb);
T_b(phrase);
T_b("( ");
T_b(type_name);
T_b(" ");
T_b(T_l(type_name));
T_b(" ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("checkLiving();");
T_b("\n");
T_b("        ");
T_b(T_l(type_name));
T_b(".checkLiving();");
T_b("\n");
if ( is_many ) {
T_b("        ");
T_b("if ( ");
T_b(ref_name);
T_b(".contains( ");
T_b(T_l(type_name));
T_b(" ) ) {");
T_b("\n");
T_b("            ");
T_b(ref_name);
T_b(".remove( ");
T_b(T_l(type_name));
T_b(" );");
T_b("\n");
if ( unlinker_is_many ) {
T_b("            ");
T_b(T_l(type_name));
T_b(".remove");
T_b(unlinker_capital_ref_name);
T_b("( this );");
T_b("\n");
} else {
T_b("            ");
T_b(T_l(type_name));
T_b(".clear");
T_b(unlinker_capital_ref_name);
T_b("();");
T_b("\n");
}
T_b("            ");
T_b("// TODO set referential attributes");
T_b("\n");
T_b("        ");
T_b("}");
T_b("\n");
} else {
T_b("        ");
T_b("if ( !(");
T_b(ref_name);
T_b(" instanceof IEmptyInstance) ) {");
T_b("\n");
T_b("            ");
T_b(ref_name);
T_b(" = ");
T_b(type_name);
T_b(".empty");
T_b(type_name);
T_b(";");
T_b("\n");
if ( unlinker_is_many ) {
T_b("            ");
T_b(T_l(type_name));
T_b(".remove");
T_b(unlinker_capital_ref_name);
T_b("( this );");
T_b("\n");
} else {
T_b("            ");
T_b(T_l(type_name));
T_b(".clear");
T_b(unlinker_capital_ref_name);
T_b("();");
T_b("\n");
}
T_b("            ");
T_b("// TODO set referential attributes");
T_b("\n");
T_b("        ");
T_b("}");
T_b("\n");
}
T_b("        ");
T_b("else throw new LinkException( \"Cannot unlink non-linked relationship.\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
