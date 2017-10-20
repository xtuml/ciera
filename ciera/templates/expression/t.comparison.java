.if ( primitives )
  .if ( "eq" == operation )
${left_operand_body} == ${right_operand_body}\
  .elif ( "ne" == operation )
${left_operand_body} != ${right_operand_body}\
  .elif ( "gt" == operation )
${left_operand_body} > ${right_operand_body}\
  .elif ( "lt" == operation )
${left_operand_body} < ${right_operand_body}\
  .elif ( "ge" == operation )
${left_operand_body} >= ${right_operand_body}\
  .elif ( "le" == operation )
${left_operand_body} <= ${right_operand_body}\
  .end if
.else
  .if ( "eq" == operation )
${left_operand_body}.equals( ${right_operand_body} )\
  .elif ( "ne" == operation )
!${left_operand_body}.equals( ${right_operand_body} )\
  .elif ( "gt" == operation )
${left_operand_body}.compareTo( ${right_operand_body} ) > 0\
  .elif ( "lt" == operation )
${left_operand_body}.compareTo( ${right_operand_body} ) < 0\
  .elif ( "ge" == operation )
${left_operand_body}.compareTo( ${right_operand_body} ) >= 0\
  .elif ( "le" == operation )
${left_operand_body}.compareTo( ${right_operand_body} ) <= 0\
  .end if
.end if
