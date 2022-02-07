    public static void main( String[] args ) {
        IApplication app = new ${app_name}();
        app.setup( args, null );
        if ( Arrays.asList(args).contains("-v") || Arrays.asList(args).contains("--version") ) {
            app.printVersions();
        }
        else {
            app.initialize();
            app.start();
        }       
    }
