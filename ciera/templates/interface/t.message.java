.if ( "declaration" == location )
    public void ${message_name}(${parameter_list});
.elif ( "definition" == location )
    @Override
    public void ${message_name}(${parameter_list}) {
  .if ( inbound )
  .else
        send( new ${message_class}( "${message_name}", getPeerID(), getID()${invocation_parameter_list} ) );
  .end if
    }
.elif ( "switch" == location )
                case "${message_name}":
                    ${message_name}(${message_parameter_list});
                    break;
.end if
