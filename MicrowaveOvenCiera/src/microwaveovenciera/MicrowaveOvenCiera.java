package microwaveovenciera;

import ciera.application.XtumlApplication;
import ciera.components.Component;
import microwaveovenciera.components.microwaveoven.MicrowaveOven;

public class MicrowaveOvenCiera extends XtumlApplication {
    
    @Override
    public void setup() {
        // set the number of threads in the system
        setNumberThreads( 1 );
        // create components
        setComponents( new Component[]{ new MicrowaveOven() } );
    }
    
    public static void main( String[] args ) {
        XtumlApplication.app = new MicrowaveOvenCiera();
        XtumlApplication.app.setup();
        XtumlApplication.app.initialize();
        XtumlApplication.app.start();
    }

}
