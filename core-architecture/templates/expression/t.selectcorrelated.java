${root_expression_body}.\
.if (set_select)
  .if (correlated_many)
flatMap\
  .else
map\
  .end if
($l{self.class_name} -> $l{self.class_name}.${self.selector_name}(${correlated_expression_body})).filter(ObjectInstance::notEmpty).distinct()\
.else
${self.selector_name}(${correlated_expression_body})\
.end if
