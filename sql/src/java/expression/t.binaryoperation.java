if ( self->invocation ) {
T_b(left_operand_body);
T_b(".");
T_b(operator);
T_b("( ");
T_b(right_operand_body);
T_b(" )");
} else if ( variable_assignment ) {
T_b("setSymbol( \"");
T_b(left_operand_body);
T_b("\", ");
T_b(right_operand_body);
T_b(" )");
} else {
T_b(left_operand_body);
T_b(" ");
T_b(operator);
T_b(" ");
T_b(right_operand_body);
T_b("");
}
