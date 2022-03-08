package ${self.package};

${imports}

public class ${self.name} extends \
.if (self.transient)
AbstractDomain implements Domain {
.else
AbstractPersistentDomain implements PersistentDomain {
.end if

.if (utilities != "")
    // utilities
    ${utilities}

.end if
.if (ports != "")
    // ports
    ${ports}

.end if
    public ${self.name}() {
        this("${self.name}");
    }

    public ${self.name}(String name) {
        super(name);
        ${utility_initializers}
        ${port_initializers}
    }

.if (functions != "")
    // domain functions
    ${functions}

.end if
.if (class_operations != "")
    // static class operations
    ${class_operations}

.end if
.if (relationship_modifiers != "")
    // relates and unrelates
    ${relationship_modifiers}

.end if
.if (port_accessors != "")
    // port accessors
    ${port_accessors}

.end if
.if (init != "")
    // component initialization function
    @Override
    public void initialize() {
        ${init}\
        super.initialize();
    }

.end if
.if (port_handlers != "")
    @Override
    public MessageTarget getMessageTarget(Class<? extends MessageTarget> targetClass) {
        ${port_handlers}else {
            throw new MessageTargetException("Could not find port to deliver message", null, null);
        }
    }

.end if
    @Override
    public Port getPort(final String portName) {
        switch (portName) {
        ${port_handlers2}\
        default:
            throw new IllegalArgumentException("Port with the name '" + portName + "' does not exist in domain: " + this);
        }
    }

.if (csm_handlers != "")
    @Override
    public void consumeEvent(Event event) {
        Class<?> cls = event.getClass().getDeclaringClass();
        ${csm_handlers}else {
            throw new EventTargetException("Could not find state machine to handle event", this, event);
        }
    }

.end if
.if (csm_persists != "")
    @Override
    public void persist(ObjectOutputStream out) throws IOException {
        super.persist(out);
        ${csm_persists}\
    }

.end if
.if (csm_loads != "")
    @Override
    public void load(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.load(in);
        ${csm_loads}\
    }

.end if
    @Override
    public ${self.name} getDomain() {
        return (${self.name}) super.getDomain();
    }

    public static Domain provider() {
        return Optional.ofNullable(Application.getInstance().getDomain("${self.name}")).orElseGet(${self.name}::new);
    }


}
