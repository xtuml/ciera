package ${package_name};

${import_block}

public class ${port_name} extends ${extends_class} implements ${interface_name} {

    // inbound messages
${inbound_message_block}

	  // outbound messages
${outbound_message_block}

	  @Override
    public void receive( IMessage message ) {
		    if ( null != message ) {
			      switch ( message.getName() ) {
${message_switch_block}
				        default:
					          break;
			      }
		    }
	  }

}
