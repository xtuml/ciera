.if (self.invocation)
  .if (self.util)
${self.operator}(${operand_body})\
  .else
${operand_body}.${self.operator}()\
  .end if
.else
${self.operator}${operand_body}\
.end if
