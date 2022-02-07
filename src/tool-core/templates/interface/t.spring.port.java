package ${port.package};

${imports}
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;

@Controller
public class ${port.name} extends ${port.base_class}<${port.comp_name}> implements ${port.iface_name} {

    private SimpMessagingTemplate template;

    private static ${port.name} singleton;
    public static ${port.name} Singleton() {
        return singleton;
    }

    @Autowired
    public ${port.name}( SimpMessagingTemplate template ) {
        super ( ${port.comp_name}.Singleton(), null );
        singleton = this;
        this.template = template;
    }

    // inbound messages
${inbound_message_block}
    // unpack parameters from message instance
${unpack_message_block}

    // outbound messages
${outbound_message_block}

${sendmethod}

    @Override
    public void deliver( IMessage message ) throws XtumlException {
    }

    @Override
    public String getName() {
        return "${port.port_name}";
    }

}
