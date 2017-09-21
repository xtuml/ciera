if ( is_formalizer ) {
if ( is_empty ) {
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public synchronized void relateTo");
T_b(target_key_letters);
T_b("AcrossR");
T_b(rel_numb);
T_b(phrase);
T_b("( ");
T_b(target_type_name);
T_b(" ");
T_b(target_inst_name);
T_b(" ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("throw new EmptyInstanceException( \"Relate of empty instance\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
} else {
T_b("    ");
T_b("public synchronized void relateTo");
T_b(target_key_letters);
T_b("AcrossR");
T_b(rel_numb);
T_b(phrase);
T_b("( ");
T_b(target_type_name);
T_b(" ");
T_b(target_inst_name);
T_b(" ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("checkLiving();");
T_b("\n");
T_b("        ");
T_b(target_inst_name);
T_b(".checkLiving();");
T_b("\n");
if ( is_many ) {
T_b("        ");
T_b("if ( !(");
T_b(ref_name);
T_b(".contains( ");
T_b(target_inst_name);
T_b(" )) ) {");
T_b("\n");
T_b("            ");
T_b(ref_name);
T_b(".add( ");
T_b(target_inst_name);
T_b(" );");
T_b("\n");
if ( corresponding_is_many ) {
T_b("            ");
T_b(target_inst_name);
T_b(".add");
T_b(capital_ref_name);
T_b("( this );");
T_b("\n");
} else {
T_b("            ");
T_b(target_inst_name);
T_b(".set");
T_b(capital_ref_name);
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
T_b(target_inst_name);
T_b(";");
T_b("\n");
if ( corresponding_is_many ) {
T_b("            ");
T_b(target_inst_name);
T_b(".add");
T_b(capital_ref_name);
T_b("( this );");
T_b("\n");
} else {
T_b("            ");
T_b(target_inst_name);
T_b(".set");
T_b(capital_ref_name);
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
}
} else {
if ( is_empty ) {
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public void relateTo");
T_b(target_key_letters);
T_b("AcrossR");
T_b(rel_numb);
T_b(phrase);
T_b("( ");
T_b(target_type_name);
T_b(" ");
T_b(target_inst_name);
T_b(" ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("throw new EmptyInstanceException( \"Relate of empty instance\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
} else {
T_b("    ");
T_b("public void relateTo");
T_b(target_key_letters);
T_b("AcrossR");
T_b(rel_numb);
T_b(phrase);
T_b("( ");
T_b(target_type_name);
T_b(" ");
T_b(target_inst_name);
T_b(" ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b(target_inst_name);
T_b(".relateTo");
T_b(class_kl);
T_b("AcrossR");
T_b(rel_numb);
T_b(corresponding_phrase);
T_b("( this );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
}
}
