.if (public_iface)
public  \
.end if
static class $c{self.name} extends PortMessage implements Message {

    private static final long serialVersionUID = ${self.serial_version_id}L;

    \
.if (public_iface)
public \
.end if
$c{self.name}(${parameter_list}) {
        super($_u{self.name}, Map.<String, Object>of(${invocation_parameter_list}));
    }

}

