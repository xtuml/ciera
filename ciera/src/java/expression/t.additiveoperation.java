if ( 0==strcmp("add",operation) ) {
T_b(left_operand_body);
T_b(" + ");
T_b(right_operand_body);
T_b("");
} else if ( 0==strcmp("sub",operation) ) {
T_b(left_operand_body);
T_b(" - ");
T_b(right_operand_body);
T_b("");
}
