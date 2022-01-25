public void ${self.msg_name}(${parameter_list}) \
.if ( inbound )
${body}
.else
{
    if (satisfied()) {
        if (getPeer() instanceof ${self.iface_name} && getContext().equals(getPeer().getContext())) {
            ((${self.iface_name}) getPeer()).${self.msg_name}(${invocation_parameter_list});
        } else {
            send(new ${self.iface_name}.$c{self.msg_name}(${invocation_parameter_list}));
        }
    } else ${body}
}
.end if
