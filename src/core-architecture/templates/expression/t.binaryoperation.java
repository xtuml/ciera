.if (self.cast != "")
((${self.cast})\
.end if
.if (self.invocation)
  .if (self.comparison_operator != "")
    .if (self.util)
(${self.comparison_operator}(${left_operand_body}, ${right_operand_body}) ${operator} 0)\
    .else
(${left_operand_body}.${self.comparison_operator}(${right_operand_body}) ${operator} 0)\
    .end if
  .else
    .if (self.util)
${operator}(${left_operand_body}, ${right_operand_body})\
    .else
${left_operand_body}.${operator}(${right_operand_body})\
    .end if
  .end if
.else
${left_operand_body} ${operator} ${right_operand_body}\
.end if
.if (self.cast != "")
)\
.end if
