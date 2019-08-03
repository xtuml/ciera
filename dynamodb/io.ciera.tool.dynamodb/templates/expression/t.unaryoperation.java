.if ( self.invocation )
${operand_body}.${self.operator}()\
.else
${self.operator}${operand_body}\
.end if
