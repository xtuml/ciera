package ${self.package};

${imports}

public interface ${self.name} extends \
.if (self.supertype_name != "")
${self.supertype_name}, \
.end if
Port {

    // messages IDs
    ${message_id_declarations}\

    // message classes
    ${message_declarations}\

    // to provider messages
    ${to_provider_message_block}\

    // from provider messages
    ${from_provider_message_block}\

}
