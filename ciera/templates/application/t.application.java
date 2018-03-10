package ${self.package};

${imports}

public class ${self.name} implements IApplication {

    IComponent[] components;
    IRunContext[] executors;

    public MicrowaveOvenApplication() {
        components = new IComponent[$t{num_component_instances}];
        executors = new IRunContext[$t{num_executors}];
    }

    @Override
    public void setup() {
        for ( int i = 0; i < executors.length; i++ )
            executors[i] = new ApplicationExecutor( "${self.name}Executor" + i );
${component_instantiations}
    }

    @Override
    public void initialize() {
        for ( IComponent component : components ) {
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

    public static void main( String[] args ) {
        IApplication app = new ${self.name}();
        app.setup();
        app.initialize();
        app.start();
    }

}
