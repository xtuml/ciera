if ( primitives ) {
if ( 0==strcmp("eq",operation) ) {
T_b(left_operand_body);
T_b(" == ");
T_b(right_operand_body);
T_b("");
} else if ( 0==strcmp("ne",operation) ) {
T_b(left_operand_body);
T_b(" != ");
T_b(right_operand_body);
T_b("");
} else if ( 0==strcmp("gt",operation) ) {
T_b(left_operand_body);
T_b(" > ");
T_b(right_operand_body);
T_b("");
} else if ( 0==strcmp("lt",operation) ) {
T_b(left_operand_body);
T_b(" < ");
T_b(right_operand_body);
T_b("");
} else if ( 0==strcmp("ge",operation) ) {
T_b(left_operand_body);
T_b(" >= ");
T_b(right_operand_body);
T_b("");
} else if ( 0==strcmp("le",operation) ) {
T_b(left_operand_body);
T_b(" <= ");
T_b(right_operand_body);
T_b("");
}
} else {
if ( 0==strcmp("eq",operation) ) {
T_b(left_operand_body);
T_b(".equals( ");
T_b(right_operand_body);
T_b(" )");
} else if ( 0==strcmp("ne",operation) ) {
T_b("!");
T_b(left_operand_body);
T_b(".equals( ");
T_b(right_operand_body);
T_b(" )");
} else if ( 0==strcmp("gt",operation) ) {
T_b(left_operand_body);
T_b(".compareTo( ");
T_b(right_operand_body);
T_b(" ) > 0");
} else if ( 0==strcmp("lt",operation) ) {
T_b(left_operand_body);
T_b(".compareTo( ");
T_b(right_operand_body);
T_b(" ) < 0");
} else if ( 0==strcmp("ge",operation) ) {
T_b(left_operand_body);
T_b(".compareTo( ");
T_b(right_operand_body);
T_b(" ) >= 0");
} else if ( 0==strcmp("le",operation) ) {
T_b(left_operand_body);
T_b(".compareTo( ");
T_b(right_operand_body);
T_b(" ) <= 0");
}
}
