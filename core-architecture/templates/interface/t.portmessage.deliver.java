case ${self.iface_name}.$_u{self.msg_name}:
    runMessageHandler(message, () -> ${self.msg_name}(${message_parameter_list}));
    break;
