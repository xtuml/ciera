if ( self->invocation ) {
if ( self->util ) {
T_b(operator);
T_b("( ");
T_b(left_operand_body);
T_b(", ");
T_b(right_operand_body);
T_b(" )");
} else {
T_b(left_operand_body);
T_b(".");
T_b(operator);
T_b("( ");
T_b(right_operand_body);
T_b(" )");
}
} else {
T_b(left_operand_body);
T_b(" ");
T_b(operator);
T_b(" ");
T_b(right_operand_body);
T_b("");
}
