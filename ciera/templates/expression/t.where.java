.if ( self->multiplicity_many )
${root_expression_body}.where( selected -> ${where_expression_body} )\
.else
${root_expression_body}.anyWhere( selected -> ${where_expression_body} )\
.end if
