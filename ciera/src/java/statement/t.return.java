if ( isvoid ) {
T_b("return;");
T_b("\n");
} else {
T_b("return ");
T_b(ret_expression_body);
T_b("\n");
}
