            case ${self.iface_name}.SIGNAL_NO_$u{self.msg_name}:
.if ( type_name == "void" )
                ${self.msg_name}(${message_parameter_list});
                return null;
.else
                return ${self.msg_name}(${message_parameter_list});
.end if
