.if ( is_event_creation )
  .if ( "" != parameter_list )
new ${base_name}.${self.invoked_name}(getRunContext(), ${parameter_list})\
  .else
new ${base_name}.${self.invoked_name}(getRunContext())\
  .end if
.else
${base_name}.${self.invoked_name}(${parameter_list})\
.end if
