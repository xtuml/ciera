package ${port.package};

${imports}

public class ${port.name} extends ${port.base_class}<${port.comp_name}> implements ${port.iface_name} {

    public ${port.name}( ${port.comp_name} context, IPort<?> peer ) {
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
        return "${port.port_name}";
    }

}
