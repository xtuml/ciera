if ( 0!=strcmp("",oal) ) {
T_b(indent);
T_b("// ");
T_b(oal);
T_b("\n");
}
T_b(indent);
T_b("context().unrelate_");
T_b(self->rel_name);
T_b("( ");
T_b(form_expr);
T_b(", ");
T_b(part_expr);
T_b(" );");
T_b("\n");
