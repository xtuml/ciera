T_b(indent);
T_b("// ");
T_b(oal);
T_b("\n");
T_b(indent);
T_b("");
if ( 0==strcmp("",ret_expression_body) ) {
T_b("return;");
T_b("\n");
} else {
T_b("return ");
T_b(ret_expression_body);
T_b(";");
T_b("\n");
}
