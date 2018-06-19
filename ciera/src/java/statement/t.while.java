if ( 0!=strcmp("",oal) ) {
T_b(indent);
T_b("// ");
T_b(oal);
T_b("\n");
}
T_b(indent);
T_b("while ( ");
T_b(cond_expr);
T_b(" ) ");
T_b(control_block);
T_b("");
