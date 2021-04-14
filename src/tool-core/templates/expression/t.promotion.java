.if (self.cast)
  .if ("" == cast_function)
((${type_name})(${root_expression_body}))\
  .else
${root_expression_body}.${cast_function}()\
  .end if
.else
new ${type_name}(${root_expression_body})\
.end if
