package ${self.package};

${imports}

.if (spring_controller )
@Controller
.end if
public class ${self.name} extends ${self.base_class}<${self.comp_name}> implements ${self.iface_name} {
.if (spring_controller )
	private SimpMessagingTemplate template;
.end if
	private static ${self.name} singleton;
	public static ${self.name} Singleton() {
		return singleton;
	}
    public ${self.name}( ${self.comp_name} context, IPort<?> peer ) {
        super( context, peer );
        singleton = this;
    }
.if (spring_controller )
    @Autowired
    public ${self.name}( SimpMessagingTemplate template ) {
		super ( ${self.comp_name}.Singleton(), null );
		singleton = this;
        this.template = template;
	}
.end if


    // inbound messages
${inbound_message_block}

    // outbound messages
${outbound_message_block}

.if (spring_controller )
    @Override 
    public void send(IMessage message) throws XtumlException {
    	String msgname = message.getName();
     	String payload = (String)message.getParms().toString();
    	SpringMsg springmsg = new SpringMsg ( msgname, payload );
        String topic = "/topic/HRuser/";
    	this.template.convertAndSend( topic, springmsg );
    }
.end if

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
