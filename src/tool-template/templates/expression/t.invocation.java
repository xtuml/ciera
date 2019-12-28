.if ( is_event_creation )
new ${base_name}.${self.invoked_name}(getRunContext(), context().getId()\
  .if ( "" != parameter_list )
, ${parameter_list}\
  .end if
)\
.else
${base_name}.${self.invoked_name}(${parameter_list})\
.end if
