package ${iface.package};

${imports}

public interface ${iface.name} {

    // inbound messages from WebSocket to xtUML
${in_message_block}

    // outbound messages from xtUML to WebSocket
${out_message_block}

}
