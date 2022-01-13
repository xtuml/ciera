.if ("one" == self.multiplicity)
Optional.of(${root_expression_body}).filter(selected -> ${where_expression_body}).orElse(${default_value})\
.else
${root_expression_body}\
  .if (sorted)
${sort_expr}\
  .end if
..filter(selected -> ${where_expression_body})\
  .if ("any" == self.multiplicity)
..findAny().orElse(${default_value})\
  .end if
.end if
