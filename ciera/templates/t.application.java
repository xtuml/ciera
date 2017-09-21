package ${package_name};

${import_block}

public class ${application_name} extends Application {

    @Override
    public void setup() {
        // set the number of threads in the system
        setNumberThreads( ${num_threads} );
        // create components
        setComponents( new IComponent[]{ ${component_block} } );
    }

    public static void main( String[] args ) {
        Application.app = new ${application_name}();
        Application.app.setup();
        Application.app.initialize();
        Application.app.start();
    }

}
