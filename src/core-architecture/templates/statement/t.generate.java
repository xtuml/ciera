.if ( "" != actions )
${indent}/* ${actions} */
.end if
${prefix}${indent}context().generate(${evt_expr});
