            ${self.test} (event instanceof ${class_name}Impl.${self.event_name}) {
.if ( cant_happen )
                throw new CantHappenException("${self.state_name} -> ${self.event_name}: Event cannot happen");
.elif ( event_ignored )
                /* ${self.state_name} -> ${self.event_name}: Event ignored */ 
.else
                System.out.printf("TXN: %-15s: %-50s -> %-50s", "${class_name}", "${self.state_name}", "${self.end_state_name}");
                System.out.println();
                ${self.end_state_name}_entry_action(${parameter_list});
                setCurrentState(${self.end_state_name});
.end if
            }
