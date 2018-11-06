package ${self.package};

${imports}

public class ${self.name} implements IApplication {

    IComponent<?>[] components;
    IRunContext[] executors;

    public ${self.name}() {
        components = new IComponent<?>[$t{num_component_instances}];
        executors = new IRunContext[$t{num_executors}];
    }

    @Override
    public void setup( String[] args ) {
        for ( int i = 0; i < executors.length; i++ )
            executors[i] = new ApplicationExecutor( "${self.name}Executor" + i, args );
${component_instantiations}
    }

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
        for ( IRunContext executor : executors ) {
            executor.start();
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

    public static void main( String[] args ) {
        IApplication app = new ${self.name}();
        app.setup( args );
        if ( Arrays.asList(args).contains("-v") || Arrays.asList(args).contains("--version") ) {
        	  app.printVersions();
        }
        else {
            app.initialize();
            app.start();
        }
    }

}
