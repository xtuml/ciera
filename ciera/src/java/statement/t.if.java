T_b(indent);
T_b("if ( ");
T_b(cond_expr);
T_b(" ) ");
T_b(then_block);
T_b("\n");
if ( 0!=strcmp("",else_block) ) {
T_b(indent);
T_b("else ");
T_b(else_block);
T_b("\n");
}
