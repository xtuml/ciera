.if (override_message)
@Override
.end if
public ${type_name} ${self.msg_name}(${parameter_list}) \
.if ( ( inbound ) or ( not public_iface ) )
${body}
.else
{
    if (satisfied()) {
        \
  .if (type_name != "void")
return \
  .end if
((${self.iface_name}) getPeer()).${self.msg_name}(${invocation_parameter_list});
    } else ${body}
}
.end if
