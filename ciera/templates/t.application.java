package ${package_name};

${import_block}

public class ${application_name} extends XtumlApplication {
    
    @Override
    public void setup() {
        // set the number of threads in the system
        setNumberThreads( ${num_threads} );
        // create components
        setComponents( new Component[]{ ${component_block} } );
    }
    
    public static void main( String[] args ) {
        XtumlApplication.app = new ${application_name}();
        XtumlApplication.app.setup();
        XtumlApplication.app.initialize();
        XtumlApplication.app.start();
    }

}
