${root_expression_body}.\
.if (set_select)
  .if (multiplicity_many)
flatMap\
  .else
map\
  .end if
(${self.class_name}::${self.selector_name}).distinct()\
.else
${self.selector_name}()\
.end if
