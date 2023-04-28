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
implements Domain, ${comp_iface.package}.${comp_iface.name} {

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
    public void initialize() {
        super.initialize();
        ${init}\
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

    // standalone application
    public static void main(String[] args) {
        CommandLine.initialize(args);
        final Logger logger = LoggerFactory.getLogger(${self.name}.class);

        logger.debug("MAIN: processing arguments");
        CommandLine.getInstance().registerValue("-config", "Config file path", Conditionality.Optional, "path", Conditionality.Required, Multiplicity.Single);

        final String configPath = CommandLine.getInstance().getOptionValue("-config");
        if (configPath != "") {
            logger.debug("MAIN: loading configuration");
            Architecture.getInstance().loadConfig(Path.of(configPath));
        }

        // create domain
        logger.debug("MAIN: creating domains");
        final ${self.name} $l{self.name} = new ${self.name}();

        // create scheduler
        final SimpleScheduler scheduler = new SimpleScheduler($l{self.name});

        // initialize domain
        logger.debug("MAIN: initializing domains");
        scheduler.execute($l{self.name}::initialize);

        // schedule tasks forever in a single thread
        logger.debug("MAIN: starting up...");
        while (scheduler.hasNext()) {
            scheduler.next().run();
        }
        logger.debug("MAIN: shutting down...");
    }
}
