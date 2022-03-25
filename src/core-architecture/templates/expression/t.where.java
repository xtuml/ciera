.if ("one" == self.multiplicity)
Stream.of(${root_expression_body}).filter(ObjectInstance::notEmpty)\
.else
${root_expression_body}\
.end if
.if (where_expression_body != "true")
..filter(selected -> ${where_expression_body})\
.end if
