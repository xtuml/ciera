.if ( "" != oal )
${indent}// ${oal}
.end if
${prefix}${indent}context().generate(${evt_expr}.to\
.if ( self.to_self )
Self\
.end if
(${target_expr}));
