package ${self.package};

${imports}

.if spring_controller
@SpringBootApplication
.end if
public class ${self.name} implements IApplication {

    private IComponent<?>[] components;
    private ApplicationExecutor[] executors;
.if spring_controller
    private static ${self.name} singleton;
.end if

    public ${self.name}() {
        components = new IComponent<?>[$t{num_component_instances}];
        executors = new ApplicationExecutor[$t{num_executors}];
        singleton = this;
.if spring_controller
        setup( null, null );
        initialize();
.end if;
    }

    @Override
    public void run() {
        for (ApplicationExecutor executor : executors) {
            executor.run();
        }
    }

    @Override
    public void setup( String[] args, ILogger logger ) {
        for ( int i = 0; i < executors.length; i++ ) {
            if ( null != logger ) {
                executors[i] = new ApplicationExecutor( "${self.name}Executor" + i, args, logger );
            }
            else {
                executors[i] = new ApplicationExecutor( "${self.name}Executor" + i, args );
            }
        }
${component_instantiations}${component_satisfactions}    }

${component_getters}
    @Override
    public void initialize() {
        for ( IComponent<?> component : components ) {
            component.getRunContext().execute( new GenericExecutionTask() {
                @Override
                public void run() throws XtumlException {
                    component.initialize();
                }
            });
        }
    }

    @Override
    public void start() {
        if (1 == executors.length) {
            executors[0].run();
        }
        else {
            for ( ApplicationExecutor executor : executors ) {
                executor.start();
            }
        }
    }

    @Override
    public void printVersions() {
        io.ciera.runtime.Version.printVersion();
        for ( IComponent<?> c : components ) {
            System.out.printf("%s: %s (%s)", c.getClass().getName(), c.getVersion(), c.getVersionDate());
            System.out.println();
        }
    }

    @Override
    public void stop() {
        for ( ApplicationExecutor executor : executors ) {
            executor.execute(new HaltExecutionTask());
        }
    }

    public static void main( String[] args ) {
.if spring_controller
    	SpringApplication.run( ${self.name}.class, args );
    	singleton.start();
.else
        IApplication app = new ${self.name}();
        app.setup( args, null );
        if ( Arrays.asList(args).contains("-v") || Arrays.asList(args).contains("--version") ) {
            app.printVersions();
        }
        else {
            app.initialize();
            app.start();
        }
.end if        
    }

}
