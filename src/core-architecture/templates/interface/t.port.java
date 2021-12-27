package ${self.package};

${imports}

public class ${self.name} extends Terminator implements ${self.iface_name} {

    public ${self.name}(${self.comp_name} domain) {
        super("${self.name}", domain);
    }

    // inbound messages
    ${inbound_message_block}\

    // outbound messages
    ${outbound_message_block}\

.if (message_switch_block != "")
    @Override
    public void deliver(Message message) {
        if (message != null) {
            ${self.iface_name}.Messages messageId = (${self.iface_name}.Messages) message.getId();
            switch (messageId) {
            ${message_switch_block}\
            default:
                throw new IllegalArgumentException("Message not implemented by this port. TODO");
            }
        } else {
            throw new IllegalArgumentException("Cannot deliver null message.");
        }
    }

.end if
    @Override
    public ${self.comp_name} getDomain() {
        return (${self.comp_name}) super.getDomain();
    }

}
