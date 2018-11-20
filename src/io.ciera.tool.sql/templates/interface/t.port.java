package ${self.package};

${imports}

public class ${self.name} extends ${self.base_class}<${self.comp_name}> implements ${self.iface_name} {

    public ${self.name}( ${self.comp_name} context, IPort<?> peer ) {
        super( context, peer );
    }

    // inbound messages
${inbound_message_block}

    // outbound messages
${outbound_message_block}

    @Override
    public void deliver( IMessage message ) throws XtumlException {
        if ( null == message ) throw new BadArgumentException( "Cannot deliver null message." );
        switch ( message.getId() ) {
${message_switch_block}\
        default:
            throw new BadArgumentException( "Message not implemented by this port." );
        }
    }

${extra_parameters}

    @Override
    public String getName() {
        return "${self.port_name}";
    }

}
