.if ( self->invocation )
${left_operand_body}.${self.operator}( ${right_operand_body} )\
.else
${left_operand_body} ${self.operator} ${right_operand_body}\
.end if
