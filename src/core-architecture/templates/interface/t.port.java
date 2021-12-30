package ${self.package};

${imports}

public class ${self.name} extends AbstractPort implements ${self.iface_name} {

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
            switch (message.getId()) {
            ${message_switch_block}\
            default:
                throw new PortMessageException("Message not implemented by this port", getDomain(), this, message);
            }
        } else {
            throw new PortMessageException("Cannot deliver null message", getDomain(), this, message);
        }
    }

.end if
    @Override
    public ${self.comp_name} getDomain() {
        return (${self.comp_name}) super.getDomain();
    }

}
