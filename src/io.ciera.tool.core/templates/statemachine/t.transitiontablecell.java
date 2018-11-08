.if ( cant_happen )
CANT_HAPPEN\
.elif ( event_ignored )
IGNORE\
.else
(event) -> {${self.end_state_name}_entry_action(${parameter_list}); return ${self.end_state_name};}\
.end if
