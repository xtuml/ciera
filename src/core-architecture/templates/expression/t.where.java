.if ("one" == self.multiplicity)
Optional.of(${root_expression_body}).filter(selected -> ${where_expression_body}).orElse(${default_value})\
.else
${root_expression_body}\
  .if (where_expression_body != "true")
..filter(selected -> ${where_expression_body})\
  .end if
  .if ("any" == self.multiplicity)
..findAny().orElse(${default_value})\
  .end if
.end if
