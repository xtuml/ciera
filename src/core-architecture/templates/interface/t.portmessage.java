    public ${type_name} ${self.msg_name}(${parameter_list}) throws XtumlException \
.if ( inbound )
${body}
.else
{
        if ( satisfied() ) {
  .if ( type_name == "void" )
            send(new ${self.iface_name}.S$c{self.msg_name}(${invocation_parameter_list}));
  .else
            throw new UnsupportedOperationException("Cannot serialize a message that carries a return value.");
  .end if
        } else ${body}\
    }
.end if
