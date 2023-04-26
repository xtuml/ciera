package ${self.package};

${imports}

public class ${self.name} {

    private static final Logger logger = LoggerFactory.getLogger(${self.name}.class);

    public static void main(String[] args) {

        // process command line
        logger.debug("MAIN: processing arguments");
        CommandLine.initialize(args);
        CommandLine.getInstance().registerValue("-config", "Config file path", Conditionality.Optional, "path", Conditionality.Required, Multiplicity.Single);

        // load configuration
        logger.debug("MAIN: loading config");
        final String configPath = CommandLine.getInstance().getOptionValue("-config");
        if (configPath != "" && Files.exists(Path.of(configPath))) {
            logger.debug("MAIN: loading configuration");
            Architecture.getInstance().loadConfig(Path.of(configPath));
        }

        // create domains
        logger.debug("MAIN: creating domains");
        ${domains}\
        
        // create scheduler
        final SimpleScheduler scheduler = new SimpleScheduler(${domain_list});

        // create satisfactions
        ${satisfactions}\

        // initialize domains
        logger.debug("MAIN: initializing domains");
        ${domain_initializations}\

        // schedule tasks forever in a single thread
        logger.debug("MAIN: starting up...");
        while (scheduler.hasNext()) {
            scheduler.next().run();
        }
        logger.debug("MAIN: shutting down...");

    }
    
}
