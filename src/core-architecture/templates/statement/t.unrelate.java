.// only evaluate the expression once
.if (not_empty next_coupled_unrelate)
final ${form_expression.type_reference} _link${self.statement_number} = ${form_expr};
getDomain().unrelate_${self.rel_name}(_link${self.statement_number}, ${part_expr});
.elif (not_empty prev_coupled_unrelate)
getDomain().unrelate_${self.rel_name}(_link${prev_coupled_unrelate.statement_number}, ${part_expr});
.else
getDomain().unrelate_${self.rel_name}(${form_expr}, ${part_expr});
.end if
