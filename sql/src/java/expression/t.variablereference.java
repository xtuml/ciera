if ( 0==strcmp("",array_init_expression_body) ) {
T_b("((");
T_b(type_name);
T_b(")getSymbol( \"");
T_b(self->var_name);
T_b("\" ))");
} else {
T_b("((");
T_b(type_name);
T_b(")setSymbol( \"");
T_b(self->var_name);
T_b("\", new ");
T_b(type_base_name);
T_b("[");
T_b(array_init_expression_body);
T_b("+1] ))");
}
