.if (self.invocation)
  .if (self.comparison_operator != "")
    .if (use_util)
(${self.comparison_operator}(${left_operand_body}, ${right_operand_body}) ${operator} 0)\
    .else
(${left_operand_body}.${self.comparison_operator}(${right_operand_body}) ${operator} 0)\
    .end if
  .else
    .if (use_util)
${operator}(${left_operand_body}, ${right_operand_body})\
    .else
${left_operand_body}.${operator}(${right_operand_body})\
    .end if
  .end if
.else
${left_operand_body} ${operator} ${right_operand_body}\
.end if
