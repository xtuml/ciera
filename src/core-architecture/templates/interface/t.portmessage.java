    public ${type_name} ${self.msg_name}(${parameter_list}) throws XtumlException \
.if ( inbound )
${body}
.else
{
        if ( satisfied() ) {
  .if ( type_name == "void" )
            send(new ${self.iface_name}.S$c{self.msg_name}(${invocation_parameter_list}));
  .else
            return (${type_name}) syncSend(new ${self.iface_name}.S$c{self.msg_name}(${invocation_parameter_list}));
  .end if
        } else ${body}\
    }
.end if
