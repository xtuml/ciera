package ${self.package};

${imports}

public class ${self.name} extends \
.if (self.supertype_name != "")
${self.supertype_name} \
.else
AbstractPort \
.end if
.if (implements_interface)
implements ${self.iface_name} \
.end if
{

    public ${self.name}(${self.comp_name} domain) {
        super("${self.name}", domain);
    }

    public ${self.name}(String name, ${self.comp_name} domain) {
        super(name, domain);
    }

    // inbound messages
    ${inbound_message_block}\

    // outbound messages
    ${outbound_message_block}\

    @Override
    public ${self.comp_name} getDomain() {
        return (${self.comp_name}) super.getDomain();
    }

}
