if ( self->invocation ) {
T_b(operand_body);
T_b(".");
T_b(self->operator);
T_b("()");
} else {
T_b(self->operator);
T_b(operand_body);
T_b("");
}
