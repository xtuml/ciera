public ${type_name} ${self.msg_name}(${parameter_list}) \
.if ( inbound )
${body}
.else
{
    if (satisfied()) {
        if (getPeer() instanceof ${self.iface_name} && getContext().equals(getPeer().getContext())) {
            \
  .if (type_name != "void")
return \
  .end if
((${self.iface_name}) getPeer()).${self.msg_name}(${invocation_parameter_list});
        } else {
  .if (is_async)
            send(new ${self.iface_name}.$c{self.msg_name}(${invocation_parameter_list}));
  .else
            throw new UnsupportedOperationException("Synchronous delivery of remote messages is not supported");
  .end if
        }
    } else ${body}
}
.end if
