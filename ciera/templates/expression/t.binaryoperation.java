.if ( self->invocation )
${left_operand_body}.${operator}( ${right_operand_body} )\
.elif ( variable_assignment )
setSymbol( "${left_operand_body}", ${right_operand_body} )\
.else
${left_operand_body} ${operator} ${right_operand_body}\
.end if
