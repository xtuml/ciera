case ${self.event_class_name}.$u_{self.event_name}:
.if (cant_happen)
    return StateMachine.cannotHappen(currentState, event);
.elif (event_ignored)
    return StateMachine.ignore(currentState, event);
.else
.if ((not has_txn_action) and (not has_entry_action))
    return () -> States.$u_{end_state_name}; 
.else
    return () -> {
  .if (has_txn_action)
        ${self.state_name}${self.event_name}TxnTo${end_state_name}Action(${parameter_list});
  .end if
  .if (has_entry_action)
        ${end_state_name}EntryAction(${state_parameter_list});
  .end if
        return States.$u_{end_state_name};
    };
  .end if
.end if
