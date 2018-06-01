T_b(indent);
T_b("// ");
T_b(oal);
T_b("\n");
if ( 0==strcmp("",ret_expression_body) ) {
if ( handle_locals ) {
T_b(indent);
T_b("popSymbolTable();");
T_b("\n");
}
T_b(indent);
T_b("return;");
T_b("\n");
} else {
if ( returns_var ) {
T_b(indent);
T_b("_return_value = ");
T_b(ret_expression_body);
T_b(";");
T_b("\n");
}
if ( handle_locals ) {
T_b(indent);
T_b("popSymbolTable();");
T_b("\n");
}
if ( returns_var ) {
T_b(indent);
T_b("return _return_value;");
T_b("\n");
} else {
T_b(indent);
T_b("return ");
T_b(ret_expression_body);
T_b(";");
T_b("\n");
}
}
