if ( 0==strcmp("one",self->multiplicity) ) {
T_b(root_expression_body);
T_b(".oneWhere( selected -> ");
T_b(where_expression_body);
T_b(" )");
} else if ( 0==strcmp("any",self->multiplicity) ) {
T_b(root_expression_body);
T_b(".anyWhere( selected -> ");
T_b(where_expression_body);
T_b(" )");
} else {
T_b(root_expression_body);
T_b(".where( selected -> ");
T_b(where_expression_body);
T_b(" )");
}
