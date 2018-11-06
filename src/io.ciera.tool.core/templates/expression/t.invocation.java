.if ( is_event_creation )
new ${base_name}.${self.invoked_name}(${parameter_list})\
.else
${base_name}.${self.invoked_name}(${parameter_list})\
.end if
