if ( self->multiplicity_many ) {
T_b(root_expression_body);
T_b(".where( selected -> ");
T_b(where_expression_body);
T_b(" )");
} else {
T_b(root_expression_body);
T_b(".anyWhere( selected -> ");
T_b(where_expression_body);
T_b(" )");
}
