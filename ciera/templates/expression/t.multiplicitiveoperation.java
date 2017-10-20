.if ( "mul" == operation )
${left_operand_body} * ${right_operand_body}\
.elif ( "div" == operation )
${left_operand_body} / ${right_operand_body}\
.elif ( "mod" == operation )
${left_operand_body} % ${right_operand_body}\
.end if
