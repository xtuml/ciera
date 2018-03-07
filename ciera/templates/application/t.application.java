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
            executors[i] = new ApplicationExecutor();
${component_instantiations}
    }

    @Override
    public void initialize() {
        for ( IComponent component : components ) {
            component.getRunContext().execute( new Runnable() {
                @Override
                public void run() {
                    try {
                        component.initialize();
                    }
                    catch ( XtumlException e ) {
                        component.getRunContext().getExceptionHandler().handle( e );
                    }
                }
            });
        }
    }

    @Override
    public void start() {
        if ( executors.length > 0 ) {
            if ( executors.length > 1 ) {
                for ( int i = 1; i < executors.length; i++ ) {
                    new Thread( executors[i] ).start();
                }
            }
            executors[0].run();
        }
    }

    public static void main( String[] args ) {
        IApplication app = new ${self.name}();
        app.setup();
        app.initialize();
        app.start();
    }

}
