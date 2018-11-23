.if ( "one" == self.multiplicity )
${root_expression_body}.oneWhere( selected -> ${where_expression_body} )\
.elif ( "any" == self.multiplicity )
${root_expression_body}.anyWhere( selected -> ${where_expression_body} )\
.else
${root_expression_body}.where( selected -> ${where_expression_body} )\
.end if
