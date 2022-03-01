package ${self.package};

${imports}

public class ${self.name} extends \
.if (self.transient)
AbstractDomain implements Domain {
.else
AbstractPersistentDomain implements PersistentDomain {
.end if

    // utilities
    ${utilities}

    // ports
    ${ports}

    public ${self.name}(String name) {
        super(name);
        ${utility_initializers}
        ${port_initializers}
    }

    // domain functions
    ${functions}

    // static class operations
    ${class_operations}

    // relates and unrelates
    ${relationship_modifiers}

    // port accessors
    ${port_accessors}

    // component initialization function
    @Override
    public void initialize() {
        ${init}
    }

.if (port_handlers != "")
    @Override
    public MessageTarget getMessageTarget(Class<? extends MessageTarget> targetClass) {
        ${port_handlers}else {
            throw new MessageTargetException("Could not find port to deliver message", null, null);
        }
    }

.end if
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

}
