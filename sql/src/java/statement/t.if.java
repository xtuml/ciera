if ( 0!=strcmp("",if_oal) ) {
T_b(indent);
T_b("// ");
T_b(if_oal);
T_b("\n");
}
if ( ! self->is_else_if ) {
T_b(indent);
T_b("");
}
T_b("if ( ");
T_b(cond_expr);
T_b(" ) ");
T_b(then_block);
T_b("");
if ( 0!=strcmp("",else_block) ) {
if ( 0!=strcmp("",else_oal) ) {
T_b(indent);
T_b("// ");
T_b(else_oal);
T_b("\n");
}
T_b(indent);
T_b("else ");
T_b(else_block);
T_b("");
}
