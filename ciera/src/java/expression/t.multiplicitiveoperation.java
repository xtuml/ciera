if ( 0==strcmp("mul",operation) ) {
T_b(left_operand_body);
T_b(" * ");
T_b(right_operand_body);
T_b("");
} else if ( 0==strcmp("div",operation) ) {
T_b(left_operand_body);
T_b(" / ");
T_b(right_operand_body);
T_b("");
} else if ( 0==strcmp("mod",operation) ) {
T_b(left_operand_body);
T_b(" % ");
T_b(right_operand_body);
T_b("");
}
