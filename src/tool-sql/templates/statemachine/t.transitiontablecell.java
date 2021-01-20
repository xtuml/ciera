.if ( cant_happen )
CANT_HAPPEN\
.elif ( event_ignored )
IGNORE\
.else
(event) -> {\
.if (has_txn_action)
${self.state_name}_${evt_name}_txn_to_${end_state_name}_action(${parameter_list});\
.end if
.if (has_entry_action)
${end_state_name}_entry_action(${state_parameter_list});\
.end if
return ${end_state_name};}\
.end if
