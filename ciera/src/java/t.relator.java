T_b("    ");
T_b("public void relateTo");
T_b(type_key_letters);
T_b("AcrossR");
T_b(rel_numb);
T_b(phrase);
T_b("( ");
T_b(type_name);
T_b(" ");
T_b(type_name);
T_b(" ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("checkLiving();");
T_b("\n");
T_b("        ");
T_b(type_name);
T_b(".checkLiving();");
T_b("\n");
if ( is_many ) {
T_b("        ");
T_b("if ( !(");
T_b(ref_name);
T_b(".contains( ");
T_b(type_name);
T_b(" )) ) {");
T_b("\n");
T_b("            ");
T_b(ref_name);
T_b(".add( ");
T_b(type_name);
T_b(" );");
T_b("\n");
if ( linker_is_many ) {
T_b("            ");
T_b(type_name);
T_b(".add");
T_b(linker_capital_ref_name);
T_b("( this );");
T_b("\n");
} else {
T_b("            ");
T_b(type_name);
T_b(".set");
T_b(linker_capital_ref_name);
T_b("( this );");
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
T_b("if ( ");
T_b(ref_name);
T_b(" instanceof IEmptyInstance ) {");
T_b("\n");
T_b("            ");
T_b(ref_name);
T_b(" = ");
T_b(type_name);
T_b(";");
T_b("\n");
if ( linker_is_many ) {
T_b("            ");
T_b(type_name);
T_b(".add");
T_b(linker_capital_ref_name);
T_b("( this );");
T_b("\n");
} else {
T_b("            ");
T_b(type_name);
T_b(".set");
T_b(linker_capital_ref_name);
T_b("( this );");
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
T_b("else throw new LinkException( \"Cannot link to already linked relationship.\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
