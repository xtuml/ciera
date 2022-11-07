package ${self.package};

.if (imports != "")
${imports}\


.end if
public class ${self.name} extends \
.if (self.supertype_name != "")
${self.supertype_name} {
.else
AbstractDomain \
.end if
implements Domain {

.if (utilities != "")
    ${utilities}\

.end if
.if (ports != "")
    ${ports}\

.end if
    public ${self.name}() {
        super("${self.name}");
.if (utility_initializers != "")
        ${utility_initializers}\
.end if
.if (port_initializers != "")
        ${port_initializers}\
.end if
    }

.if (functions != "")
    ${functions}\

.end if
.if (class_operations != "")
    ${class_operations}\

.end if
.if (relationship_modifiers != "")
    ${relationship_modifiers}\

.end if
.if (port_accessors != "")
    ${port_accessors}\

.end if
.if (init != "")
    @Override
    public void initialize(final ExecutorService runtime) {
        super.initialize(runtime);
        ${init}\
    }

.end if
.if (port_handlers != "")
    @Override
    public MessageTarget getMessageTarget(Class<? extends MessageTarget> targetClass) {
        ${port_handlers}else {
            return super.getMessageTarget(targetClass);
        }
    }

.end if
.if (port_handlers2 != "")
    @Override
    public Port getPort(final String portName) {
        switch (portName) {
        ${port_handlers2}\
        default:
            return super.getPort(portName);
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
