.if ( self.invocation )
  .if ( self.util )
${operator}( ${left_operand_body}, ${right_operand_body} )\
  .else
${left_operand_body}.${operator}( ${right_operand_body} )\
  .end if
.else
${left_operand_body} ${operator} ${right_operand_body}\
.end if
