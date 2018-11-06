        case (${self.state_name}):
.if ( "" != transition_table_cells )
${transition_table_cells}
            else {
                throw new StateMachineException("Unrecognized event");
            }
.end if
            break;
